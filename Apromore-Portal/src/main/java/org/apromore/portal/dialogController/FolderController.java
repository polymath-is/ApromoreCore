package org.apromore.portal.dialogController;

import org.apromore.manager.client.ManagerService;
import org.apromore.model.FolderType;
import org.apromore.model.ProcessSummaryType;
import org.apromore.portal.common.UserSessionManager;
import org.zkoss.spring.SpringUtil;
import org.zkoss.zk.ui.Component;
import org.zkoss.zk.ui.event.Event;
import org.zkoss.zk.ui.event.ForwardEvent;
import org.zkoss.zk.ui.util.Clients;
import org.zkoss.zk.ui.util.GenericForwardComposer;
import org.zkoss.zul.Button;
import org.zkoss.zul.Hbox;
import org.zkoss.zul.Html;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by IntelliJ IDEA.
 * User: Igor
 * Date: 2/07/12
 * Time: 5:08 PM
 * To change this template use File | Settings | File Templates.
 */
public class FolderController extends GenericForwardComposer {

    private FolderType searchedFolder = null;

    @Override
    public void doAfterCompose(Component comp) throws Exception {
        super.doAfterCompose(comp);
    }

    public void onFolderClick$folderWindow(Event event) {
        ForwardEvent eventx = (ForwardEvent) event;
        Hbox folderOptions = (Hbox) eventx.getOrigin().getTarget().getParent().getFellow("workspaceOptionsPanel").getFellow("folderOptions");
        Button btnRenameFolder = (Button) folderOptions.getFellow("btnRenameFolder");
        Button btnRemoveFolder = (Button) folderOptions.getFellow("btnRemoveFolder");

        try {
            String idsString = eventx.getOrigin().getData().toString();

            UserSessionManager.setSelectedFolderIds(new ArrayList<Integer>());
            UserSessionManager.setSelectedProcessIds(new ArrayList<Integer>());
            UserSessionManager.getMainController().clearProcessVersions();

            if (!idsString.isEmpty()) {
                folderOptions.setVisible(true);
                String[] ids = idsString.split(",");
                if (ids.length == 1) {
                    btnRenameFolder.setVisible(true);
                } else {
                    btnRenameFolder.setVisible(false);
                }
                List<Integer> folderIds = new ArrayList<Integer>();
                List<Integer> processIds = new ArrayList<Integer>();
                boolean canDelete = true;
                boolean canRename = true;
                for (String id : ids) {
                    String[] tokens = id.split("~");

                    if (tokens.length == 3) {
                        if (tokens[0].equals("f")) {
                            folderIds.add(Integer.parseInt(tokens[1]));
                        } else {
                            processIds.add(Integer.parseInt(tokens[1]));
                        }
                        int access = Integer.parseInt(tokens[2]);
                        if (access < 4) {
                            canDelete = false;
                        }
                        if (access < 2) {
                            canRename = false;
                        }
                    }
                }

                btnRemoveFolder.setVisible(canDelete);
                btnRenameFolder.setVisible(btnRenameFolder.isVisible() && canRename);
                UserSessionManager.setSelectedFolderIds(folderIds);
                UserSessionManager.setSelectedProcessIds(processIds);
                UserSessionManager.getMainController().updateSelectedListBox(processIds);
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        Clients.evalJavaScript("bindTiles();");
    }

    public void onFolderDblClick$folderWindow(Event event) {
        getData(event, true);
    }

    public void onBreadCrumbClick$breadCrumbsWindow(Event event) {
        getData(event, false);
    }

    public void getData(Event event, boolean isFolder) {
        ForwardEvent eventx = (ForwardEvent) event;
        try {
            int selectedFolderId = Integer.parseInt(eventx.getOrigin().getData().toString());
            Html html = (Html) eventx.getOrigin().getTarget().getParent().getFellow("folders");

            Hbox folderOptions = (Hbox) eventx.getOrigin().getTarget().getParent().getFellow("workspaceOptionsPanel").getFellow("folderOptions");
            Button btnRenameFolder = (Button) folderOptions.getFellow("btnRenameFolder");
            Button btnRemoveFolder = (Button) folderOptions.getFellow("btnRemoveFolder");
            btnRenameFolder.setVisible(false);
            btnRemoveFolder.setVisible(false);

            if (html != null) {
                FolderType selectedFolder = null;
                List<FolderType> availableFolders = UserSessionManager.getCurrentFolder() == null || UserSessionManager.getCurrentFolder().getId() == 0 ? UserSessionManager.getTree() : UserSessionManager.getCurrentFolder().getFolders();
                List<ProcessSummaryType> availableProcesses = ((ManagerService) SpringUtil.getBean("managerClient")).getProcesses(UserSessionManager.getCurrentUser().getId(), selectedFolderId);

                if (isFolder) {
                    for (FolderType folder : availableFolders) {
                        if (folder.getId() == selectedFolderId) {
                            selectedFolder = folder;
                            break;
                        }
                    }
                } else {
                    FolderType folderType = new FolderType();
                    folderType.setId(0);
                    for (FolderType folder : UserSessionManager.getTree()) {
                        folderType.getFolders().add(folder);
                    }
                    findFolder(folderType, selectedFolderId);
                    selectedFolder = searchedFolder;
                }

                if (selectedFolder != null) {
                    List<FolderType> breadcrumbFolders = UserSessionManager.getMainController().getService().getBreadcrumbs(UserSessionManager.getCurrentUser().getId(), selectedFolderId);
                    Collections.reverse(breadcrumbFolders);
                    String content = "<table cellspacing='0' cellpadding='5' id='breadCrumbsTable'><tr>";

                    int i = 0;
                    for (FolderType breadcrumb : breadcrumbFolders) {
                        if (i > 0) {
                            content += "<td style='font-size: 9pt;'>&gt;</td>";
                        }
                        content += "<td><a class='breadCrumbLink' style='cursor: pointer; font-size: 9pt; color: Blue; text-decoration: underline;' id='" + breadcrumb.getId().toString() + "'>" + breadcrumb.getFolderName() + "</a></td>";
                        i++;
                    }

                    content += "</tr></table>";
                    UserSessionManager.getMainController().breadCrumbs.setContent(content);
                    Clients.evalJavaScript("bindBreadcrumbs();");

                    UserSessionManager.setPreviousFolder(UserSessionManager.getCurrentFolder());
                    UserSessionManager.setCurrentFolder(selectedFolder);

                    UserSessionManager.getMainController().reloadProcessSummaries();
                    loadWorkspace(html, selectedFolder.getFolders(), availableProcesses);
                    Clients.evalJavaScript("bindTiles();");
                }
            }
        } catch (Exception ex) {

        }
    }

    private void findFolder(FolderType folder, int selectedFolderId) {
        if (folder.getId() == selectedFolderId) {
            searchedFolder = folder;
            return;
        }

        for (FolderType folderType : folder.getFolders()) {
            findFolder(folderType, selectedFolderId);
        }
    }

    public void loadWorkspace(Html html, List<FolderType> folders, List<ProcessSummaryType> processes) {
        String content = "<ul class='workspace'>";

        for (FolderType folder : folders) {
            int access = 1;
            if (folder.isHasWrite()) {
                access = 2;
            }
            if (folder.isHasOwnership()) {
                access = 4;
            }
            content += String.format("<li class='folder' id='%d' access='%d'><div>%s</div></li>", folder.getId(), access, folder.getFolderName());
        }

        for (ProcessSummaryType process : processes) {
            int access = 1;
            if (process.isHasWrite()) {
                access = 2;
            }
            if (process.isHasOwnership()) {
                access = 4;
            }
            content += String.format("<li class='process' id='%d' access='%d'><div>%s</div></li>", process.getId(), access, process.getName().length() > 20 ? process.getName().substring(0, 16) + "" : process.getName());
        }

        content += "</ul>";
        html.setContent(content);
    }

}
