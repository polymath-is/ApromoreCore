package org.apromore.graph.canonical;

import org.apromore.cpf.EdgeType;
import org.jbpt.graph.abs.IDirectedGraph;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

/**
 * Interface to the Canonical Process Format.
 *
 * @author Cameron James
 */
public interface ICanonical<E extends IEdge<N>, N extends INode> extends IDirectedGraph<E, N> {

    /**
     * returns the graphs URI.
     * @return the Graphs URI
     */
    String getUri();

    /**
     * Sets the URI for the graph.
     * @param newUri the graphs URI.
     */
    void setUri(final String newUri);

    /**
     * returns the version of the graph
     * @return the version of the graph
     */
    String getVersion();

    /**
     * Sets the version for the graph.
     * @param newVersion the version URI.
     */
    void setVersion(final String newVersion);

    /**
     * returns the author of the graph
     * @return the author of the graph
     */
    String getAuthor();

    /**
     * Sets the author for the graph.
     * @param newAuthor the author URI.
     */
    void setAuthor(final String newAuthor);

    /**
     * returns the creation date of the graph
     * @return the creation date of the graph
     */
    String getCreationDate();

    /**
     * Sets the creation date for the graph.
     * @param newCreationDate the creation date URI.
     */
    void setCreationDate(final String newCreationDate);

    /**
     * returns the modified date of the graph
     * @return the modified date of the graph
     */
    String getModifiedDate();

    /**
     * Sets the modified date for the graph.
     * @param newModifiedDate the modified date URI.
     */
    void setModifiedDate(final String newModifiedDate);


    /**
     * Returns the entry Node.
     * @return the entry node.
     */
    CPFNode getEntry();

    /**
     * Set the entry Node.
     * @param entry the entry Node.
     */
    void setEntry(final CPFNode entry);

    /**
     * Returns the Exit Node.
     * @return the exit node.
     */
    CPFNode getExit();

    /**
     * Set the Exit Node.
     * @param exit the exit Node.
     */
    void setExit(CPFNode exit);

    /**
     * @return a {@link java.util.Collection} of all {@link ICPFObject}s of this {@link INode}.
     */
    Set<ICPFObject> getObjects();

    /**
     * Add a given {@link ICPFObject} to this {@link INode}.
     * @param object to add to this {@link INode}
     */
    void addObject(ICPFObject object);

    /**
     * @return a {@link java.util.Collection} of all {@link ICPFResource}s of this {@link INode}.
     */
    Set<ICPFResource> getResources();

    /**
     * Add a given {@link ICPFResource} to this {@link INode}.
     * @param newResource to add to this {@link INode}
     */
    void addResource(ICPFResource newResource);

    /**
     * Add a collection of {@link ICPFResource} to this {@link INode}.
     * @param newResources to add to this {@link INode}
     */
    void setResources(Set<ICPFResource> newResources);

    /**
     * Adds a specific Edge to the the Graph, we need specific details that the edge holds.
     * @param edge the edge to add.
     * @return the edge we added.
     */
    public E addEdge(E edge);

    /**
     * Finds and updates the edge with the extra details in the new edge.
     * @param edge the edge with updated details.
     * @param edgeType
     * @param expr
     */
    E updateEdge(CPFEdge edge, EdgeType edgeType, CPFExpression expr);

    /**
     * Return all {@link CPFNode} which precede the given {@link CPFNode} in the {@link CPFEdge}.
     * @param fn {@link CPFNode} to start from
     * @return {@link Collection} containing all predecessors of the given {@link CPFNode}
     */
    public Collection<CPFNode> getAllPredecessors(CPFNode fn);

    /**
     * Return all {@link CPFNode} which succeed the given {@link CPFNode} in the {@link CPFEdge}.
     * @param fn {@link CPFNode} to start from
     * @return {@link Collection} containing all successors of the given {@link CPFNode}
     */
    public Collection<CPFNode> getAllSuccessors(CPFNode fn);



    /**
     * sets a Node Property.
     * @param nodeId the node Id
     * @param propertyName the property name
     * @param propertyValue the property value
     */
    void setNodeProperty(final String nodeId, final String propertyName, final String propertyValue);

    /**
     * Set the Node Properties.
     * @param nodeId the Node Id
     * @param propertyName the property Name
     * @return the Node Property we found, or null.
     */
    String getNodeProperty(String nodeId, String propertyName);

    /**
     * @param properties the properties
     */
    void setProperties(Map<String, IAttribute> properties);

    /**
     * return the properties
     * @return the map of properties
     */
    Map<String, IAttribute> getProperties();

    /**
     * return a property.
     * @param name the name of the property
     * @return the value of the property we are searching for.
     */
    IAttribute getProperty(String name);

    /**
     * Sets a property.
     * @param name  the name of the property
     * @param value the simple value text value of the property
     * @param any the complex XML value of the property
     */
    void setProperty(String name, String value, java.lang.Object any);

    /**
     * Sets a property only the simple text based value.
     * @param name  the name of the property
     * @param value the simple value text value of the property
     */
    void setProperty(String name, String value);



    /**
     * Add flow to this net.
     * This method ensures net stays bipartite.
     * @param from Source node.
     * @param to   Target node.
     * @return Edge added to this net; <tt>null</tt> if no flow was added.
     */
    public E addEdge(N from, N to);

    /**
     * Add node to this net.
     * @param node Node to add.
     * @return Node added to this net; <tt>null</tt> if no node was added.
     */
    public N addNode(N node);

    /**
     * Add nodes to this net.
     * @param nodes Nodes to add.
     * @return Nodes added to this net.
     */
    public Collection<N> addNodes(Collection<N> nodes);

    /**
     * Remove node from this net.
     * @param node Node to remove.
     * @return Node removed from this net; <tt>null</tt> if node was not removed.
     */
    public N removeNode(N node);

    /**
     * Remove nodes from this net.
     * @param nodes Nodes to remove.
     * @return Nodes removed from this net.
     */
    public Collection<N> removeNodes(Collection<N> nodes);

    /**
     * Remove flow from this net.
     * @param edge Edge to remove.
     * @return Edge removed from this net; <tt>null</tt> if no flow was removed.
     */
    public E removeFlow(E edge);

    /**
     * Remove flow from this net.
     * @param edge Edge to remove.
     * @return Edge removed from this net.
     */
    public Collection<E> removeFlows(Collection<E> edge);


    /**
     * Get node by it's Id.
     * @return Node of this net.
     */
    public N getNode(String id);

    /**
     * Get nodes of this net.
     * @return Nodes of this net.
     */
    public Set<N> getNodes();

    /**
     * Get flow relation of this net.
     * @return Edge relation of this net.
     */
    public Set<E> getEdges();

    /**
     * Get postset of a given node.
     * @param node Node.
     * @return Postset of the given node.
     */
    public Set<N> getPostset(N node);

    /**
     * Get postset of given nodes.
     * @param nodes Nodes.
     * @return Postset of given nodes.
     */
    public Set<N> getPostset(Collection<N> nodes);

    /**
     * Get preset of a given node.
     * @param node Node.
     * @return Preset of the given node.
     */
    public Set<N> getPreset(N node);

    /**
     * Get preset of the given nodes.
     *
     * @param nodes Nodes.
     * @return Preset of the given nodes.
     */
    public Set<N> getPreset(Collection<N> nodes);

    /**
     * Get source nodes of this net.
     * A node is a source node if it has empty preset.
     *
     * @return Source nodes of this net.
     */
    public Set<N> getSourceNodes();

    /**
     * Get sink nodes of this net.
     * A node is a sink node if it has empty postset.
     *
     * @return Sink nodes of this net.
     */
    public Set<N> getSinkNodes();

    /**
     * Get minimal nodes of this net (alias of {@link ICanonical#getSourceNodes()}).
     * @return Minimal nodes of this net.
     */
    public Set<N> getMin();

    /**
     * Get maximal nodes of this net (alias of {@link ICanonical#getSinkNodes()}).
     *
     * @return Maximal nodes of this net.
     */
    public Set<N> getMax();

    /**
     * Clear this net.
     */
    public void clear();
}