/**
 * Copyright (c) 2006
 * Martin Czuchra, Nicolas Peters, Daniel Polak, Willi Tscheschner
 *
 * Permission is hereby granted, free of charge, to any person obtaining a
 * copy of this software and associated documentation files (the "Software"),
 * to deal in the Software without restriction, including without limitation
 * the rights to use, copy, modify, merge, publish, distribute, sublicense,
 * and/or sell copies of the Software, and to permit persons to whom the
 * Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING
 * FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER
 * DEALINGS IN THE SOFTWARE.
 **/

/**
 * Init namespaces
 */
if (!ORYX) {
    var ORYX = {};
}

/**
 @namespace Namespace for the Oryx core elements.
 @name ORYX.Core
 */
if (!ORYX.Core) {
    ORYX.Core = {};
}

/**
 * @class Oryx canvas.
 * <div class = 'ORYX_Editor'>: parentNode
 *      <svg id = 'editorcanvas'>: rootNode
 *          <g>: node
 *              <g class = 'stencils'>
 *                  <g class = 'me'></g>
 *                  <g class = 'children'></g>: childContainer
 *                  <g class = 'edge'></g>: edgeContainer
 *              </g>
 *              <g class = 'svgcontainer'>
 *              </g>
 *          </g>
 *      </svg>
 *      <div id = 'ORYX.Editor.provideId()' style: "position:absolute; top:5px">: htmlContainer
 *      </div>
 * </div>
 *
 *
 */
class EditorCanvas {
    /** @lends ORYX.Core.Canvas.prototype */

    /**
     * Constructor
     */
    constructor (options) {
        //arguments.callee.$.construct.apply(this, arguments);

        /**
         * Defines the current zoom level
         */
        this.zoomLevel = 1;

        if (!(options && options.width && options.height)) {

            ORYX.Log.fatal("Canvas is missing mandatory parameters options.width and options.height.");
            return;
        }

        this.resourceId = options.id;
        this.nodes = [];
        this.edges = [];

        //init svg document
        this.rootNode = ORYX.Editor.graft("http://www.w3.org/2000/svg", options.parentNode,
            ['svg', {id: this.id, width: options.width, height: options.height},
                ['defs', {}]
            ]);

        this.rootNode.setAttribute("xmlns:xlink", "http://www.w3.org/1999/xlink");
        this.rootNode.setAttribute("xmlns:svg", "http://www.w3.org/2000/svg");

        this._htmlContainer = ORYX.Editor.graft("http://www.w3.org/1999/xhtml", options.parentNode,
            ['div', {id: ORYX.Editor.provideId(), style: "position:absolute; top:5px"}]);

        this.node = ORYX.Editor.graft("http://www.w3.org/2000/svg", this.rootNode,
            ['g', {},
                ['g', {"class": "stencils"},
                    ['g', {"class": "me"}],
                    ['g', {"class": "children"}],
                    ['g', {"class": "edge"}]
                ],
                ['g', {"class": "svgcontainer"}]
            ]);

        this.node.setAttributeNS(null, 'stroke', 'black');
        this.node.setAttributeNS(null, 'font-family', 'Verdana, sans-serif');
        this.node.setAttributeNS(null, 'font-size-adjust', 'none');
        this.node.setAttributeNS(null, 'font-style', 'normal');
        this.node.setAttributeNS(null, 'font-variant', 'normal');
        this.node.setAttributeNS(null, 'font-weight', 'normal');
        this.node.setAttributeNS(null, 'line-heigth', 'normal');

        this.node.setAttributeNS(null, 'font-size', ORYX.CONFIG.LABEL_DEFAULT_LINE_HEIGHT);

        //this.bounds.set(0, 0, options.width, options.height);

        this.addEventHandlers(this.rootNode.parentNode);

        //disable context menu
        this.rootNode.oncontextmenu = function () {
            return false;
        };

        // CREATES the real editor
        this._editor = new BpmnJS({
            container: '#' + this.getContainer().getId(),
            keyboard: {
                bindTo: window
            }
        });
    }

    getScrollNode () {
        "use strict";
        return Ext.get(this.rootNode).parent("div{overflow=auto}", true);
    }

    focus () {
        //
    }

    update () {

    }

    getChildShapes () {
        return [];
    }

    getChildShapeByResourceId () {
        return undefined;
    }

    getChildById () {
        return undefined;
    }

    _traverseForUpdate (shape) {
        return true;
    }

    layout () {
        "use strict";
    }

    /**
     *
     * @param {Object} deep
     * @param {Object} iterator
     */
    getChildNodes (deep, iterator) {
        return this.nodes.clone();
    }


    /**
     * Overrides the UIObject.add method. Adds uiObject to the correct sub node.
     * @param {UIObject} uiObject
     */
    add (uiObject, index, silent) {
        //
    }

    /**
     * Overrides the UIObject.remove method. Removes uiObject.
     * @param {UIObject} uiObject
     */
    remove (uiObject, silent) {

    }

    /**
     * Creates shapes out of the given collection of shape objects and adds them to the canvas.
     * @example
     * canvas.addShapeObjects({
         bounds:{ lowerRight:{ y:510, x:633 }, upperLeft:{ y:146, x:210 } },
         resourceId:"oryx_F0715955-50F2-403D-9851-C08CFE70F8BD",
         childShapes:[],
         properties:{},
         stencil:{
           id:"Subprocess"
         },
         outgoing:[{resourceId: 'aShape'}],
         target: {resourceId: 'aShape'}
       });
     * @param {Object} shapeObjects
     * @param {Function} [eventHandler] An event handler passed to each newly created shape (as eventHandlerCallback)
     * @return {Array} A collection of ORYX.Core.Shape
     * @methodOf ORYX.Core.Canvas.prototype
     */
    addShapeObjects (shapeObjects, langs, eventHandler) {
        //
    }

    /**
     * Updates the size of the canvas, regarding to the containg shapes.
     */
    updateSize() {

    }

    getRootNode () {
        return this.rootNode;
    }

    getContainer () {
        return this.rootNode.parentNode;
    }

    getSvgContainer() {
        return this.node.childNodes[1];
    }

    getChildContainer() {
        return this.node.childNodes[0].childNodes[1];
    }

    getEdgeContainer() {
        return this.node.childNodes[0].childNodes[2];
    }

    getHTMLContainer() {
        return this._htmlContainer;
    }

    /**
     * Return all elements of the same highest level
     * @param {Object} elements
     */
    getShapesWithSharedParent(elements) {

        return [];

    }

    setSize(size, dontSetBounds) {
        //
    }

    updateScrollArea() {
        // if (Ext.isIPad && "undefined" !== window.iScroll && this.iscroll) {
        //     this.iscroll.destroy();
        //     this.iscroll = new iScroll(this.rootNode.parentNode, {
        //         touchCount: 2
        //     })
        // }
    }

    getShapeSize() {
        return new ORYX.Core.Bounds(0, 0, 0, 0);
    }

    getShapeBounds() {
        return new ORYX.Core.Bounds(0, 0, 0, 0);
    }

    /**
     * Returns an SVG document of the current process.
     * @param {Boolean} escapeText Use true, if you want to parse it with an XmlParser,
     *                    false, if you want to use the SVG document in browser on client side.
     */
    getSVGRepresentation(escapeText) {
        // Get the serialized svg image source
        var svgClone;

        return svgClone;
    }

    /**
     * Removes all nodes (and its children) that has the
     * attribute visibility set to "hidden"
     */
    _removeInvisibleElements(element) {

    }

    setLanguage(c, a) {

    }

    getLanguage() {
        //return (this.language || (this.languages || []).first()) || "de_de";
        return ORYX.I18N.Language;
    }

    migrateLanguage(a) {

    }

    _delegateEvent(event) {

    }

    setOrientation(a) {

    }

    getOrientation() {
        return undefined;
    }

    toString() {
        return "Canvas " + this.id;
    }

    deserialize(b, a) {
        //
    }

    toJSON() {
        return {};
    }
};