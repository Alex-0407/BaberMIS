/**
 * Created by 胡均 on 14-1-8.
 * @ 重庆理工大学 计算机学院
 */
;
if(document.all && !document.getElementById) {
    document.getElementById = function(id) {
        return document.all[id];
    };
}
if (!String.repeat) {
    String.prototype.repeat = function(l){
        return new Array(l+1).join(this);
    };
}

if (!String.trim) {
    String.prototype.trim = function() {
        return this.replace(/^\s+|\s+$/g,'');
    };
}

(function(window,undefined){

    var GH = window.GH || {};
    if(window.GH === undefined) window.GH = GH;

    //浏览器检测
    function isCompatible(other) {
        // Use capability detection to check requirements
        if( other===false
            || !Array.prototype.push
            || !Object.hasOwnProperty
            || !document.createElement
            || !document.getElementsByTagName
            ) {
            alert('您使用的浏览器太古老了，请更换为现代浏览器.');
            return false;
        }
        return true;
    }
    GH.isCompatible =  isCompatible;

    //获取元素
    function $() {
        var elements = new Array();

        // Find all the elements supplied as arguments
        for (var i = 0; i < arguments.length; i++) {
            var element = arguments[i];

            // If the argument is a string assume it's an id
            if (typeof element == 'string') {
                element = document.getElementById(element);
            }

            // If only one argument was supplied, return the element immediately
            if (arguments.length == 1) {
                return element;
            }

            // Otherwise add it to the array
            elements.push(element);
        }

        // Return the array of multiple requested elements
        return elements;
    };
    GH.$ = $;

    //添加事件
    function addEvent( node, type, listener ) {
        // Check compatibility using the earlier method
        // to ensure graceful degradation
        if(!isCompatible()) { return false;}
        if(!(node = $(node))) return false;

        if (node.addEventListener) {
            // W3C method
            node.addEventListener( type, listener, false );
            return true;
        } else if(node.attachEvent) {
            // MSIE method
            node['e'+type+listener] = listener;
            node[type+listener] = function(){
            	node['e'+type+listener]( window.event);
            };
            node.attachEvent( 'on'+type, node[type+listener] );
            return true;
        }

        // Didn't have either so return false
        return false;
    };
    GH.addEvent = addEvent;

    //移除事件
    function removeEvent(node, type, listener ) {
        if(!(node = $(node))) return false;
        if (node.removeEventListener) {
            node.removeEventListener( type, listener, false );
            return true;
        } else if (node.detachEvent) {
            // MSIE method
            node.detachEvent( 'on'+type, node[type+listener] );
            node[type+listener] = null;
            return true;
        }
        // Didn't have either so return false
        return false;
    };
    GH.removeEvent = removeEvent;

    /**
     * 根据class name获取元素
     * Retrieve an array of element base on a class name
     * 例如:var p = GH.getElementsByClassName("pp","*")
     */
    function getElementsByClassName(className, tag, parent){
        parent = parent || document;
        if(!(parent = $(parent))) return false;

        // 查找所有匹配的标签
        //默认匹配所有元素
        tag = tag || "*";
        var allTags = (tag == "*" && parent.all) ? parent.all : parent.getElementsByTagName(tag);
        var matchingElements = new Array();

        // 创建一个正则表达式。来判断className是否正确
        className = className.replace(/\-/g, "\\-");
        var regex = new RegExp("(^|\\s)" + className + "(\\s|$)");

        var element;
        // 查找每个元素
        for(var i=0; i<allTags.length; i++){
            element = allTags[i];
            if(regex.test(element.className)){
                matchingElements.push(element);
            }
        }

        // 返回所有匹配的元素
        return matchingElements;
    };
    GH.getElementsByClassName = getElementsByClassName;

    /**
     * 让一个元素显示或者不显示
     * Toggle the style display value between none and the default
     */
    function toggleDisplay(node, value) {
        if(!(node = $(node))) return false;
        if ( node.style.display != 'none' ) {
            node.style.display = 'none';
        } else {
            node.style.display = value || '';
        }
        return true;
    }
    GH.toggleDisplay = toggleDisplay;

    /**
     * 在referenceNode 后面插入 node
     * Insert a DOM node after another DOM node
     */
    function insertAfter(node, referenceNode) {
        if(!(node = $(node))) return false;
        if(!(referenceNode = $(referenceNode))) return false;

        return referenceNode.parentNode.insertBefore(node, referenceNode.nextSibling);
    };
    GH.insertAfter = insertAfter;

    /**
     * 从一个元素中移除所有的子元素
     * Remove all teh child nodes from an element
     */
    function removeChildren(parent) {
        if(!(parent = $(parent))) return false;

        // 当存在子节点的时候移除
        while (parent.firstChild) {
            parent.firstChild.parentNode.removeChild(parent.firstChild);
        }
        // Return the parent again so you can stack the methods
        return parent;
    };
    GH.removeChildren = removeChildren;

    /**
     * 在parent里面插入一个元素节点
     * Insert a new node as the first child.
     */
    function prependChild(parent,newChild) {
        if(!(parent = $(parent))) return false;
        if(!(newChild = $(newChild))) return false;
        if(parent.firstChild) {
            //如若存在一个子节点，则在这个子节点之前插入
            parent.insertBefore(newChild,parent.firstChild);
        } else {
            // 如果没有则直接添加
            parent.appendChild(newChild);
        }
        // 返回父元素，以便实现方法连缀
        return parent;
    }
    GH.prependChild = prependChild;

    /**
     * 在给定的环境下面执行
     * Put the given object in teh context of the given method.
     */
    function bindFunction(obj, func) {
        return function() {
            func.apply(obj,arguments);
        };
    };
    GH.bindFunction = bindFunction;

    /**
     * 得到浏览器的大小
     * @returns {{width: (Number|HTMLElement|number), height: (Number|HTMLElement|number)}}
     */
    function getBrowserWindowSize() {
        var de = document.documentElement;

        // window.innerWidth for most browsers
        // document.documentElement.clientWidth for MSIE in strict mode
        // document.body.clientWidth for MSIE in quirks mode

        return {
            'width':(window.innerWidth || (de && de.clientWidth ) || document.body.clientWidth),
            'height':(window.innerHeight || (de && de.clientHeight ) || document.body.clientHeight)
        };
    };
    GH.getBrowserWindowSize = getBrowserWindowSize;

    /**
     * Constants for note type comparison
     */
    GH.node = {
        ELEMENT_NODE                : 1,
        ATTRIBUTE_NODE              : 2,
        TEXT_NODE                   : 3,
        CDATA_SECTION_NODE          : 4,
        ENTITY_REFERENCE_NODE       : 5,
        ENTITY_NODE                 : 6,
        PROCESSING_INSTRUCTION_NODE : 7,
        COMMENT_NODE                : 8,
        DOCUMENT_NODE               : 9,
        DOCUMENT_TYPE_NODE          : 10,
        DOCUMENT_FRAGMENT_NODE      : 11,
        NOTATION_NODE               : 12
    };

    /**
     * 遍历节点
     * Walk the nodes in the DOM tree without maintaining parent/child relationships.
     */
    function walkElementsLinear(func,node) {
        var root = node || window.document;
        var nodes = root.getElementsByTagName("*");
        for(var i = 0 ; i < nodes.length ; i++) {
            func.call(nodes[i]);
        }
    };
    GH.walkElementsLinear = walkElementsLinear;

    /**
     * 递归遍历节点
     * Walk the nodes in the DOM tree maintaining parent/child relationships.
     */
    function walkTheDOMRecursive(func,node,depth,returnedFromParent) {
        var root = node || window.document;
        //使用call 防止作用于的问题
        returnedFromParent = func.call(root,depth++,returnedFromParent);
        node = root.firstChild;
        while(node) {
            walkTheDOMRecursive(func,node,depth,returnedFromParent);
            node = node.nextSibling;
        }
    };
    GH.walkTheDOMRecursive = walkTheDOMRecursive;

    /**
     *
     * Walk the nodes in the DOM tree maintaining parent/child relationships and include the node attributes as well.
     */
    function walkTheDOMWithAttributes(node,func,depth,returnedFromParent) {
        var root = node || window.document;
        returnedFromParent = func(root,depth++,returnedFromParent);
        if (root.attributes) {
            for(var i=0; i < root.attributes.length; i++) {
                walkTheDOMWithAttributes(root.attributes[i],func,depth-1,returnedFromParent);
            }
        }
        if(root.nodeType != ADS.node.ATTRIBUTE_NODE) {
            node = root.firstChild;
            while(node) {
                walkTheDOMWithAttributes(node,func,depth,returnedFromParent);
                node = node.nextSibling;
            }
        }
    };
    GH.walkTheDOMWithAttributes = walkTheDOMWithAttributes;
    /**
     * Walk the DOM recursively using a callback function
     */
    function walkTheDOM(node, func) {
        func(node);
        node = node.firstChild;
        while (node) {
            walkTheDOM(node, func);
            node = node.nextSibling;
        }
    }
    GH.walkTheDOM = walkTheDOM;

    /**
     * 用于把word-word 转换为 wordWord
     * Convert hyphenated word-word strings to camel case wordWord strings.
     */
    function camelize(s) {
        return s.replace(/-(\w)/g, function (strMatch, p1){
            return p1.toUpperCase();
        });
    }
    GH.camelize = camelize;

    /**
     * 用于把wordWord转换为word-word
     * Convert camel case wordWord strings to hyphenated word-word strings.
     */
    function uncamelize(s, sep) {
        sep = sep || '-';
        return s.replace(/([a-z])([A-Z])/g, function (strMatch, p1, p2){
            return p1 + sep + p2.toLowerCase();
        });
    }
    GH.uncamelize = uncamelize;

    /**
     * 事件部分
     * @param loadEvent
     * @param waitForImages
     * @returns {*}
     */

    /**
     * 在页面加载完成后促发事件，除了加载图片
     * Add a load event that will run when the document finishes loading - excluding images.
     */
    function addLoadEvent(loadEvent,waitForImages) {
        if(!isCompatible()) return false;

        // 如果waitForImages IS true 则添加常规事件
        if(waitForImages) {
            return addEvent(window, 'load', loadEvent);
        }

        // Otherwise use a number of different methods

        // Wrap the loadEvent method to assign the correct content for the
        // this keyword and ensure that the event doesn't execute twice
        var init = function() {

            // 如果这个函数被调用的就直接返回
            if (arguments.callee.done) return;

            // 标记该韩式已经被调用
            arguments.callee.done = true;

            // 在document环境下执行载入事件
            loadEvent.apply(document,arguments);
        };

        // 为DOMContentLoaded注册事件侦听器
        if (document.addEventListener) {
            document.addEventListener("DOMContentLoaded", init, false);
        }

        // For Safari, use a setInterval() to see if the document has loaded
        if (/WebKit/i.test(navigator.userAgent)) {
            var _timer = setInterval(function() {
                if (/loaded|complete/.test(document.readyState)) {
                    clearInterval(_timer);
                    init();
                }
            },10);
        }
        // For Internet Explorer (using conditional comments) attach a script
        // that is deferred to the end of the load process and then check to see
        // if it has loaded
        /*@cc_on @*/
        /*@if (@_win32)
         document.write("<script id=__ie_onload defer src=javascript:void(0)><\/script>");
         var script = document.getElementById("__ie_onload");
         script.onreadystatechange = function() {
         if (this.readyState == "complete") {
         init();
         }
         };
         /*@end @*/
        return true;
    }
    GH.addLoadEvent = addLoadEvent;

    /**
     * 阻止事件的传播
     * Stop the propagation of an event
     */
    function stopPropagation(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        if(eventObject.stopPropagation) {
            eventObject.stopPropagation();
        } else {
            eventObject.cancelBubble = true;
        }
    }
    GH.stopPropagation = stopPropagation;

    /**
     * 阻止事件的默认行为
     * Prevents the default event in the event flow (such as following the href in an anchor).
     */
    function preventDefault(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        if(eventObject.preventDefault) {
            eventObject.preventDefault();
        } else {
            eventObject.returnValue = false;
        }
    }
    GH.preventDefault = preventDefault;

    /**
     * 得到事件对象
     * Retrieves the event object in a cross-browser way.
     */
    function getEventObject(W3CEvent) {
        return W3CEvent || window.event;
    }
    GH.getEventObject = getEventObject;

    /**
     * 得到事件源
     * Retrieves the element targeted by the event.
     */
    function getTarget(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        // Check if the target is W3C or MSIE
        var target = eventObject.target || eventObject.scrElement;
        // Reassign the target to the parent
        // if it is a text node like in Safari
        if(target.nodeType == ADS.node.TEXT_NODE) {
            target = node.parentNode;
        }
        return target;

    }
    GH.getTarget = getTarget;

    /**
     * 返回哪个鼠标被点击了
     * Determine which mouse button was pressed
     */
    function getMouseButton(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        // Initialize an object wit the appropriate properties
        var buttons = {
            'left':false,
            'middle':false,
            'right':false
        };
        // Check the toString value of the eventObject
        // W3C Dom object have a toString method and in this case it
        // should be MouseEvent
        if(eventObject.toString && eventObject.toString().indexOf('MouseEvent') != -1) {
            // W3C Method
            switch(eventObject.button) {
                case 0: buttons.left = true; break;
                case 1: buttons.middle = true; break;
                case 2: buttons.right = true; break;
                default: break;
            }
        } else if(eventObject.button) {
            // MSIE method
            switch(eventObject.button) {
                case 1: buttons.left = true; break;
                case 2: buttons.right = true; break;
                case 3:
                    buttons.left = true;
                    buttons.right = true;
                    break;
                case 4: buttons.middle = true; break;
                case 5:
                    buttons.left = true;
                    buttons.middle = true;
                    break;
                case 6:
                    buttons.middle = true;
                    buttons.right = true;
                    break;
                case 7:
                    buttons.left = true;
                    buttons.middle = true;
                    buttons.right = true;
                    break;
                default: break;
            }
        } else {
            return false;
        }
        return buttons;

    }
    GH.getMouseButton = getMouseButton;

    /**
     * 得到鼠标的位置 在 document 上面
     * Get the position of the pointer in the document.
     */
    function getPointerPositionInDocument(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        var x = eventObject.pageX || (eventObject.clientX + (document.documentElement.scrollLeft || document.body.scrollLeft));
        var y=  eventObject.pageY || (eventObject.clientY + (document.documentElement.scrollTop || document.body.scrollTop));
        //x and y now contain the coordinates of the mouse relative to the document origin
        return {'x':x,'y':y};
    }
    GH.getPointerPositionInDocument = getPointerPositionInDocument;

    /**
     *  得到按键的Code 和 value ;
     * Get the key pressed from the event object
     */
    function getKeyPressed(eventObject) {
        eventObject = eventObject || getEventObject(eventObject);
        var code = eventObject.keyCode;
        var value = String.fromCharCode(code);
        return {'code':code,'value':value};
    }
    GH.getKeyPressed = getKeyPressed;

    /**
     * 样式部分
     * 样式操作
     */

    /**
     * 通过ID修改单个元素的样式
     * Changes the style of a single element by id
     */
    function setStyleById(element, styles) {
        // 取得对象的引用
        if(!(element = $(element))) return false;
        // 循环遍历 styles 对象并应用每个属性
        for (property in styles) {
            if(!styles.hasOwnProperty(property)) continue;

            if(element.style.setProperty) {
                //DOM2 样式规范
                element.style.setProperty(uncamelize(property,'-'), styles[property],null);
            } else {
                //备用方法
                element.style[camelize(property)] = styles[property];
            }
        }
        return true;
    }
    GH.setStyle = setStyleById;
    GH.setStyleById = setStyleById;


    /**
     * Changes the style of multiple elements by class name
     */
    function setStylesByClassName(parent, tag, className, styles) {
        if(!(parent = $(parent))) return false;
        var elements = getElementsByClassName(className, tag, parent);
        for (var e = 0 ; e < elements.length ; e++) {
            setStyleById(elements[e], styles);
        }
        return true;
    }
    GH.setStylesByClassName = setStylesByClassName;


    /**
     * Changes the style of multiple elements by tag name
     */
    function setStylesByTagName(tagname, styles, parent) {
        parent = $(parent) || document;
        var elements = parent.getElementsByTagName(tagname);
        for (var e = 0 ; e < elements.length ; e++) {
            setStyleById(elements[e], styles);
        }
    }
    GH.setStylesByTagName = setStylesByTagName;

    /**
     * Retrieves the classes as an array
     */
    function getClassNames(element) {
        if(!(element = $(element))) return false;
        // Replace multiple spaces with one space and then
        // split the classname on spaces
        return element.className.replace(/\s+/,' ').split(' ');
    };
    GH.getClassNames = getClassNames;

    /**
     * Check if a class exists on an element
     */
    function hasClassName(element, className) {
        if(!(element = $(element))) return false;
        var classes = getClassNames(element);
        for (var i = 0; i < classes.length; i++) {
            // Check if the className matches and return true if it does
            if (classes[i] === className) { return true; }
        }
        return false;
    };
    GH.hasClassName = hasClassName;

    /**
     * Add a class to an element
     */
    function addClassName(element, className) {
        if(!(element = $(element))) return false;
        // Append the classname to the end of the current className
        // If there is no className, don't include the space
        element.className += (element.className ? ' ' : '') + className;
        return true;
    };
    GH.addClassName = addClassName;

    /**
     * remove a class from an element
     */
    function removeClassName(element, className) {
        if(!(element = $(element))) return false;
        var classes = getClassNames(element);
        var length = classes.length;
        //loop through the array in reverse, deleting matching items
        // You loop in reverse as you're deleting items from
        // the array which will shorten it.
        for (var i = length-1; i >= 0; i--) {
            if (classes[i] === className) { delete(classes[i]); }
        }
        element.className = classes.join(' ');
        return (length == classes.length ? false : true);
    };
    GH.removeClassName = removeClassName;

    /**
     * Add a new stylesheet
     */
    function addStyleSheet(url,media) {
        media = media || 'screen';
        var link = document.createElement('LINK');
        link.setAttribute('rel','stylesheet');
        link.setAttribute('type','text/css');
        link.setAttribute('href',url);
        link.setAttribute('media',media);
        document.getElementsByTagName('head')[0].appendChild(link);
    }
    GH.addStyleSheet = addStyleSheet;

    /**
     * Remove a stylesheet
     */
    function removeStyleSheet(url,media) {
        var styles = getStyleSheets(url,media);
        for(var i = 0 ; i < styles.length ; i++) {
            var node = styles[i].ownerNode || styles[i].owningElement;
            // Disable the stylesheet
            styles[i].disabled = true;
            // Remove the node
            node.parentNode.removeChild(node);
        }
    }
    GH.removeStyleSheet = removeStyleSheet;

    /**
     * Retrieve an array of all the stylesheets by URL
     */
    function getStyleSheets(url,media) {
        var sheets = [];
        for(var i = 0 ; i < document.styleSheets.length ; i++) {
            if (url &&  document.styleSheets[i].href.indexOf(url) == -1) { continue; }
            if(media) {
                // Normaizle the media strings
                media = media.replace(/,\s*/,',');
                var sheetMedia;

                if(document.styleSheets[i].media.mediaText) {
                    // DOM mehtod
                    sheetMedia = document.styleSheets[i].media.mediaText.replace(/,\s*/,',');
                    // Safari adds an extra comma and space
                    sheetMedia = sheetMedia.replace(/,\s*$/,'');
                } else {
                    // MSIE
                    sheetMedia = document.styleSheets[i].media.replace(/,\s*/,',');
                }
                // Skip it if the media don't match
                if (media != sheetMedia) { continue; }
            }
            sheets.push(document.styleSheets[i]);
        }
        return sheets;
    }
    GH.getStyleSheets = getStyleSheets;

    /**
     * Edit a CSS rule
     */
    function editCSSRule(selector,styles,url,media) {
        var styleSheets = (typeof url == 'array' ? url : getStyleSheets(url,media));

        for ( var i = 0; i < styleSheets.length; i++ ) {

            // Retrieve the list of rules
            // The DOM2 Style method is styleSheets[i].cssRules
            // The MSIE method is styleSheets[i].rules
            var rules = styleSheets[i].cssRules || styleSheets[i].rules;
            if (!rules) { continue; }

            // Convert to uppercase as MSIIE defaults to UPPERCASE tags.
            // this could cause conflicts if you're using case sensetive ids
            selector = selector.toUpperCase();

            for(var j = 0; j < rules.length; j++) {
                // Check if it matches
                if(rules[j].selectorText.toUpperCase() == selector) {
                    for (property in styles) {
                        if(!styles.hasOwnProperty(property)) { continue; }
                        // Set the new style property
                        rules[j].style[camelize(property)] = styles[property];
                    }
                }
            }
        }
    }
    GH.editCSSRule = editCSSRule;

    /**
     * Add a CSS rule
     */
    function addCSSRule(selector, styles, index, url, media) {
        var declaration = '';

        // Build the declaration string from the style object
        for (property in styles) {
            if(!styles.hasOwnProperty(property)) { continue; }
            declaration += property + ':' + styles[property] + '; ';
        }

        var styleSheets = (typeof url == 'array' ? url : getStyleSheets(url,media));
        var newIndex;
        for(var i = 0 ; i < styleSheets.length ; i++) {
            // Add the rule
            if(styleSheets[i].insertRule) {
                // The DOM2 Style method
                // index = length is the end of the list
                newIndex = (index >= 0 ? index : styleSheets[i].cssRules.length);
                styleSheets[i].insertRule(selector + ' { ' + declaration + ' } ',
                    newIndex);
            } else if(styleSheets[i].addRule) {
                // The Microsoft method
                // index = -1 is the end of the list
                newIndex = (index >= 0 ? index : -1);
                styleSheets[i].addRule(selector, declaration, newIndex);
            }
        }
    }
    GH.addCSSRule = addCSSRule;


    /**
     * retrieve the computed style of an element
     */
    function getStyle(element,property) {
        if(!(element = $(element)) || !property) return false;
        // Check for the value in the element's style property
        var value = element.style[camelize(property)];
        if (!value) {
            // Retrieve the computed style value
            if (document.defaultView && document.defaultView.getComputedStyle) {
                // The DOM method
                var css = document.defaultView.getComputedStyle(element, null);
                value = css ? css.getPropertyValue(property) : null;
            } else if (element.currentStyle) {
                // The MSIE method
                value = element.currentStyle[camelize(property)];
            }
        }
        // Return an empty string rather than auto so that you don't
        // have to check for auto values
        return value == 'auto' ? '' : value;
    }
    GH.getStyle = getStyle;
    GH.getStyleById = getStyle;


    /*********************************************************************
     * Ajax 请求部分
     */


    /*
     parseJSON(string,filter)
     这是一个在公共域方法http://www.json.org/json.js 的基础经行少量修改的版本
     该方法生成一个对象和数组。它可能抛出 SyntaxError 异常


     Example:

     // Parse the text. If a key contains the string 'date' then
     // convert the value to a date.

     myData = parseJSON(string,function (key, value) {
     return key.indexOf('date') >= 0 ? new Date(value) : value;
     });

     */
    function parseJSON(s,filter) {
        var j;

        function walk(k, v) {
            var i;
            if (v && typeof v === 'object') {
                for (i in v) {
                    if (v.hasOwnProperty(i)) {
                        v[i] = walk(i, v[i]);
                    }
                }
            }
            return filter(k, v);
        }

        /**
         * 解析通过三个阶段进行。第一阶段：通过正则表达式检测JSON文本，查找非JSON字符。其中，特备关注"()"
         * 和 "new",因为他们将会引起语句的调用。不过，为了安全起见，这里拒绝不希望出现的字符
         */

        if (/^("(\\.|[^"\\\n\r])*?"|[,:{}\[\]0-9.\-+Eaeflnr-u \n\r\t])+?$/.test(s)) {
            /**
             * 第二阶段：调用 eval 函数将JSON文本编译为javascript结构。其中 "{"操作符具有语法上二义性：
             * 即它可以定义一个i额语句块，也可以表示字面量。这里加几个JSON文本用括号括起来是为了消除这种二义性。
             */
            try {
                j = eval('(' + s + ')');
            } catch (e) {
                throw new SyntaxError("parseJSON");
            }
        } else {
            throw new SyntaxError("parseJSON");
        }

        /**
         * 第三阶段: 代码递归的遍历新生成的结构，而且将每个名/值对传递给一个过滤函数，
         * 以方便进行可能的转换
         */

        if (typeof filter === 'function') {
            j = walk('', j);
        }
        return j;
    };


    /**
     * 设置 XMLHttpRequest 对象的各个不同的部分
     */
   function getRequestObject(url,options) {

        // 初始化请求对象
        var req = false;
        if(window.XMLHttpRequest) {
            var req = new window.XMLHttpRequest();
        } else if (window.ActiveXObject) {
            var req = new window.ActiveXObject('Microsoft.XMLHTTP');
        }
        if(!req) return false;

        // 定义默认的选项
        options = options || {};
        options.method = options.method || 'GET';
        options.send = options.send || null;

        // 为请求的每个阶段定义不同的侦听器
        req.onreadystatechange = function() {
            switch (req.readyState) {
                case 1:
                    // 载入中
                    if(options.loadListener) {
                        options.loadListener.apply(req,arguments);
                    }
                    break;
                case 2:
                    // 加载完毕
                    if(options.loadedListener) {
                        options.loadedListener.apply(req,arguments);
                    }
                    break;
                case 3:
                    // 交互
                    if(options.ineractiveListener) {
                        options.ineractiveListener.apply(req,arguments);
                    }
                    break;
                case 4:
                    // 完成
                    // 如果失败则抛出错误
                    try {
                        if (req.status && req.status == 200) {

                            // 针对content-type 添加特殊的侦听器
                            // 由于Content-Type 头部中包含字符集: Content-Type: text/html; charset=ISO-8859-4
                            // 通过正则表达式提取所需要的部分
                            var contentType = req.getResponseHeader('Content-Type');
                            var mimeType = contentType.match(/\s*([^;]+)\s*(;|$)/i)[1];

                            switch(mimeType) {
                                case 'text/javascript':
                                case 'application/javascript':
                                    // 如果响应是javascript
                                    // req.responseText 作为回调函数的参数
                                    if(options.jsResponseListener) {
                                        options.jsResponseListener.call(req,req.responseText);
                                    }
                                    break;
                                case 'application/json':
                                    // 如果响应的是JSON,因此需要用匿名函数对 req.responseText 进行解析,将解析的返回值作为回调函数的的参数
                                    if(options.jsonResponseListener) {
                                        var json = null;
                                        try {
                                            json = parseJSON(req.responseText);
                                        } catch(e) {
                                            var json = false;
                                        }
                                        options.jsonResponseListener.call(req,json);
                                    }
                                    break;
                                case 'text/xml':
                                case 'application/xml':
                                case 'application/xhtml+xml':
                                    // 如果响应的XML，因此以 req.responseXML作为回调函数的参数
                                    if(options.xmlResponseListener) {
                                        options.xmlResponseListener.call(req,req.responseXML);
                                    }
                                    break;
                                case 'text/html':
                                    // 如果响应的HTML，因此以 req.responseXML作为回调函数的参数
                                    if(options.htmlResponseListener) {
                                        options.htmlResponseListener.call(req,req.responseText);
                                    }
                                    break;
                            }

                            // 针对响应完成的侦听器
                            if(options.completeListener) {
                                options.completeListener.apply(req,arguments);
                            }

                        } else {
                            //响应完成但是有错误
                            if(options.errorListener) {
                                options.errorListener.apply(req,arguments);
                            }
                        }

                    } catch(e) {
                        //ignore errors
                        alert('Response Error: ' + e);
                    }
                    break;
            }
        };
        // 开启请求
        req.open(options.method, url, true);
        // 添加特殊的头部信息以标识请求
        req.setRequestHeader('X-GH-Ajax-Request','AjaxRequest');
        return req;
   }
   GH.getRequestObject = getRequestObject;

    /**
     * 通过简单的包装 getRequestObject 和 send 方法发送 XMLHttpRequest请求.
     */
   function ajaxRequest(url,options) {
        var req = getRequestObject(url,options);
        return req.send(options.send);
   }
   GH.ajaxRequest = ajaxRequest;




    /**
     * 得到下一个元素节点
     * @param ele
     * @returns {Function|Node}
     */
    function getSiblingChild(ele){
        if(!ele || !ele.nextSibling) return null;
        return ele.nextSibling.nodeType==1 ? ele.nextSibling : ele.nextSibling.nextSibling;
    }
    GH.getSiblingChild = getSiblingChild;

    /******上面是基本的辅助框架***/
    /**
     * 2014-01-08 16:06:13
     * 表单验证框架
     */
    var  validationItems = [{
        name:"ltlength",//最大长度
        handler:function(formEle){
            //console.info(this.name);
            if(formEle.getAttribute(this.name)){
                var message = formEle.getAttribute(this.name+"Message") || "该项输入长度不能大于"+formEle.getAttribute(this.name);
                if(formEle.value.trim().length>parseInt(formEle.getAttribute(this.name))){
                    getSiblingChild(formEle).innerHTML += getSiblingChild(formEle).innerHTML ? ";"+message : message;
                    return false;
                }
            }
            return true;
        }
    },{
        name:"gtlength",//最小长度
        handler:function(formEle){
            if(formEle.getAttribute(this.name)){
                var message = formEle.getAttribute(this.name+"Message") || "该项输入长度不能小于"+formEle.getAttribute(this.name);
                if(formEle.value.trim().length<parseInt(formEle.getAttribute(this.name))){
                    getSiblingChild(formEle).innerHTML += getSiblingChild(formEle).innerHTML ? ";"+message : message;
                    return false;
                }
            }
            return true;
        }
    },{
        name:"nullable",//是否为空
        defaultValue:true,
        handler:function(formEle){
            //设置为false 表示不能 为空
            //console.info("nullable v");

            if(formEle.getAttribute(this.name) && formEle.getAttribute(this.name)=="false"){
                var message = formEle.getAttribute(this.name+"Message") || "不能为空";
                if(formEle.value.trim()==""){
                    getSiblingChild(formEle).innerHTML += getSiblingChild(formEle).innerHTML ? ";"+message : message;
                    return false;
                }
            }
            return true;

        }
    },{
        name:"email",
        defaultValue:/^([a-z0-9_\.-]+)@([\da-z\.-]+)\.([a-z\.]{2,6})$/,
        handler:function(formEle){

            if(formEle.getAttribute(this.name) && formEle.getAttribute(this.name)=="true"){
                var message = formEle.getAttribute(this.name+"Message") || "邮箱格式不正确";
                if(!this.defaultValue.test(formEle.value)){
                    getSiblingChild(formEle).innerHTML += getSiblingChild(formEle).innerHTML ? ";"+message : message;
                    return false;
                }
            }
            return true;
        }
    }];

    GH.form = function( form ){
        if(!(form = GH.$(form))) throw new Error("表单元素错误");
        this.formEle =  form;
        console.info("1");
        console.info(this.formEle.elements);
        var currentForm = this;
        GH.addEvent(form,"submit",function(e){
        	var result = currentForm.validation();
        	if(!result){
        		GH.preventDefault(e);
        	}
        	return result;
        });
    };

    //将表单序列化为对象
    GH.form.prototype.serializeArray = function(){
        var elements = this.formEle.elements;
        var sa = [];
        for(var i=0;i < elements.length ; i++ ){
            var ele = elements[i];
            if(ele.name && ele.name!=""){
                sa.push({
                    name:ele.name,
                    value:ele.value
                });
            }
        }
        
        return sa;
    };
    
    GH.form.prototype.validation = function(){
    	var elements = this.formEle.elements;
    	console.info("foreEle");
    	console.info(elements);
        var isOK = true;
        var errorFormItem = null;
        for (var i = 0;i<elements.length;i++){
            var ele = elements[i];
            //隐藏的表单不需要验证
            if(ele.type=="hidden") continue;
            getSiblingChild(ele).innerHTML="";
            for(var j = 0;j<validationItems.length;j++){
                var  rr = validationItems[j].handler(ele);
                //有错误就阻止表单的提交
                if(!rr && isOK){
                    isOK = false;
                    errorFormItem = elements[i];
                }
            }
        }
        //alert("22");
        if(!isOK){
            //GH.preventDefault(e);//阻止表单的提交
            errorFormItem.focus();
       }
        return isOK;
    };
    	
    
    
    GH.form.prototype.serialize = function(){
        var elements = this.formEle.elements;
        var sa = [];
        for(var i=0;i < elements.length ; i++ ){
            var ele = elements[i];
            if(ele.name && ele.name!=""){
                sa.push(ele.name+"="+ele.value);
            }
        }
        return sa.join("&");
    };
    /**
     * 添加自定义的校验事件
     * @param cusErrors
     */
    GH.cusValidation = function(validations){
        for(var i=0;i<validations.length;i++){
            validationItems.push(validations[i]);
        }
    };

})(window,undefined);