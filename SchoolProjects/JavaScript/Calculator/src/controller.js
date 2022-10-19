/**
 * calculator module
 * @module calculator
 */

// Using buttons
document.getElementById('main_input').setAttribute("value", "0");
/**
 * false flag
 * @type {boolean}
 */
var f_flag = false;
/**
 * equil flag
 * @type {boolean}
 */
var eq_flag = true;
/**
 * plus flag
 * @type {boolean}
 */
var p_flag = false;
/**
 * minus flag
 * @type {boolean}
 */
var m_flag = false;
/**
 * times flag
 * @type {boolean}
 */
var t_flag = false;
/**
 * divide flag
 * @type {boolean}
 */
var d_flag = false;
/**
 * pow flag
 * @type {boolean}
 */
var e_flag = false;
/**
 * sqrt flag
 * @type {boolean}
 */
var s_flag = false;
/**
 * Type of funkcion
 * @type {number}
 */
var fnc = 0;
/**
 * keys
 * @type {string}
 */
var keys = document.getElementsByClassName("numPad");
for (var i=keys.length; i--;) {
    keys[i].addEventListener("click",function(){
        addNumber(this.innerText);
    });
}

document.getElementById("history").addEventListener("click",function(){
    document.getElementById("historyDropdown").classList.toggle("show");
});

document.getElementById("percentageB").addEventListener("click",function(){
    percentageF();
});

document.getElementById("EQ").addEventListener("click",function(){
    equal();
});

document.getElementById("min").addEventListener("click",function(){
    plusMinus();
});

document.getElementById("plusB").addEventListener("click",function(){
    plus();
});

document.getElementById("minusB").addEventListener("click",function(){
    minus();
});

document.getElementById("timesB").addEventListener("click",function(){
    timesF();
});

document.getElementById("divideB").addEventListener("click",function(){
    divideF();
});

document.getElementById("deleteCh").addEventListener("click",function(){
    deleteC();
});

document.getElementById("div100B").addEventListener("click",function(){
    divide100();
});

document.getElementById("pi").addEventListener("click",function(){
    piF();
});

document.getElementById("eB").addEventListener("click",function(){
    eF();
});

document.getElementById("factB").addEventListener("click",function(){
    factorial();
});

document.getElementById("lnB").addEventListener("click",function(){
    lnF();
});

document.getElementById("logB").addEventListener("click",function(){
    logF();
});

document.getElementById("exB").addEventListener("click",function(){
    exF();
});

document.getElementById("xB").addEventListener("click",function(){
    xF();
});

document.getElementById("powxyB").addEventListener("click",function(){
    powxyF();
});

document.getElementById("sqrtB").addEventListener("click",function(){
    sqrtF();
});

/* When the user clicks on the button,
toggle between hiding and showing the dropdown content */


//Add number on display

/**
* Function for displaying values.
* @post The entered value appears on the display
* @param {string} x - Input character 
* @returns {void}
*/

function addNumber(x) {
    // TODO: history, position of cursor
    var result = document.getElementById('main_input');
    if(f_flag){
        result.setAttribute("value","");
        f_flag = false;
    }
    if(x === ","){
        if(!result.value.includes(",")){
            if(result.value === ""){
                result.setAttribute("value", "0" + x);
            }
            else
                result.setAttribute("value", result.value + x);
        }
    }
    else{
        if(result.value === "0" || result.value === "-0")
            result.setAttribute("value", x);
        else
            result.setAttribute("value", result.value + x);
        //result.focus();
    }
}



/**
 * Function is called after pressing button +. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
 * @returns {void}
 */

function plus(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,1);
    fnc=1;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "+");
        eq_flag = false;
    }else if(p_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "+" + result.value + "=");
        var trures = getResult(history.value,result.value,"+");
        result.setAttribute("value", trures);
        functionHistory("+");
    }else{
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"+");
        result.setAttribute("value", trures);
        functionHistory("+");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* minus
* @brief Function for displaying result of function sub() from math_lib.js.
* @description Function is called after pressing button -. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function minus(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,2);
    fnc=2;
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "-");
        eq_flag = false;
    }
    else if(m_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "-" + result.value + "=");
        //alert("result value = "+ result.value);
        var trures = getResult(history.value,result.value,"-");
        result.setAttribute("value", trures);
        functionHistory("-");
    }else{  
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"-");
        result.setAttribute("value", trures);
        functionHistory("-");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* timesF
* @brief Function for displaying result of function mul() from math_lib.js.
* @description Function is called after pressing button *. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function timesF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,3);
    fnc=3;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "*");
        eq_flag = false;
    }else if(t_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "*" + result.value + "=");
        var trures = getResult(history.value,result.value,"*");
        result.setAttribute("value", trures);
        functionHistory("*");
    }else{
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"*");
        result.setAttribute("value", trures);
        functionHistory("*");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* divideF
* @brief Function for displaying result of function div() from math_lib.js.
* @description Function is called after pressing button /. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function divideF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,4);
    fnc=4;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "/");
        eq_flag = false;
    }else if(d_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "/" + result.value + "=");
        var trures = getResult(history.value,result.value,"/");
        result.setAttribute("value", trures);
        functionHistory("/");
    }else{
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"/");
        result.setAttribute("value", trures);
        functionHistory("/");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* powxyF
* @brief Function for displaying result of function pow() from math_lib.js.
* @description Function is called after pressing button xy. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function powxyF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,5);
    fnc=5;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "^");
        eq_flag = false;
    }else if(t_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "^" + result.value + "=");
        var trures = getResult(history.value,result.value,"^");
        result.setAttribute("value", trures);
        functionHistory("^");
    }else{
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"^");
        result.setAttribute("value", trures);
        functionHistory("^");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* sqrtF
* @brief Function for displaying result of function sqrt() from math_lib.js.
* @description Function is called after pressing button yx. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function sqrtF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,6);
    fnc=6;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    if(eq_flag){
        history.setAttribute("value", result.value + "_");
        eq_flag = false;
    }else if(t_flag){
        var numberFromHistory = document.getElementsByTagName('button')[2];
        history.setAttribute("value", numberFromHistory.innerText + "_" + result.value + "=");
        var trures = getResult(history.value,result.value,"_");
        result.setAttribute("value", trures);
        functionHistory("_");
    }else{
        history.setAttribute("value", history.value + result.value + "=");
        var trures = getResult(history.value,result.value,"_");
        result.setAttribute("value", trures);
        functionHistory("_");
        flagsToTrue();
    }
    //implemented for addNumber() function
    f_flag = true;
}

/**
* factorial
* @brief Function for displaying result of function fact() from math_lib.js.
* @description Function is called after pressing button x!. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function factorial(){
    last(fnc,0);
    fnc=0;
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var val = result.value.replace(",",".");
    var res = fact(Number(val)).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value", result.value + "!=");
    result.setAttribute("value", trures);
    f_flag = true;
    lastEq = false;
}

/**
* exF
* @brief Function for displaying result of function pow() from math_lib.js.
* @description Function is called after pressing button ex. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function exF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,0);
    fnc=0;
    //load elements from inputs (display)
    
    //converter last char on display is "," we will remove it.
    
    //set history for equal (inspired from google calc)
    // 1st number
    var val = result.value.replace(",",".");
    var res = pow(Math.E,Number(val)).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value","e^" + result.value + "=");
    result.setAttribute("value", trures);
    functionHistory("^");
    //implemented for addNumber() function
    f_flag = true;
}

/**
* xF
* @brief Function for displaying result of function pow() from math_lib.js.
* @description Function is called after pressing button x2. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function xF(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    last(fnc,0);
    fnc=0;
    var val = result.value.replace(",",".");
    var res = pow(Number(val),2).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value",result.value + "^2=");
    result.setAttribute("value", trures);
    lastEq = false;
    f_flag = true;
    functionHistory("^");
}

/**
* lnf
* @brief Function for displaying result of function log() from math_lib.js.
* @description Function is called after pressing button ln. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function lnF(){
    last(fnc,0);
    fnc=0;
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var val = result.value.replace(",",".");
    var res = log(Math.E,Number(val)).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value","ln(" + result.value + ")=");
    result.setAttribute("value", trures);
    lastEq = false;
    f_flag = true;
}

/**
* logF
* @brief Function for displaying result of function log() from math_lib.js.
* @description Function is called after pressing button log10. The function works with values on display.
    Few other functions are called as last(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function logF(){
    last(fnc,0);
    fnc=0;
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var val = result.value.replace(",",".");
    var res = log(10,Number(val)).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value","log(" + result.value + ")=");
    result.setAttribute("value", trures);
    lastEq = false;
    f_flag = true;
    //functionHistory("");

}

/**
* last
* @brief Function for calling other function if neccesary.
* @description Function is called after pressing function buttons. The function is calling other function and changes value of variable depends on parametrs.
* @param {number} fnc - Number of last called calculating function
* @param {number} n - Number from which function it's been called
* @returns {void}
*/

function last(fnc,n){
    switch(fnc){
        case 1:
            if(n != 1){
                plus();
                eq_flag = true;
            }
        break;
        case 2:
            if(n != 2){
                minus();
                eq_flag = true; 
            }
        break;
        case 3:
            if(n != 3){
                timesF();
                eq_flag = true;
            }
        break;
        case 4:
            if(n != 4){
                divideF();
                eq_flag = true;
            }
        break;
        case 5:
            if(n != 5){
                powxyF();
                eq_flag = true;
            }
        break;
        case 6:
            if(n != 6){
                sqrtF();
                eq_flag = true;
            }
        break;
        default:
            break;
    }
}

/**
* equal
* @brief Function for displaying result of last called function from math_lib.js.
* @description Function is called after pressing button =. The function works with values on display.
    Few other functions are called as equalHistory(), getResult(), functionJistory(), flagsToTrue()
* @post The result appears on the display and in the history.
* @returns {void}
*/

function equal(){
    //load elements from inputs (display)
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var trures;
    fnc = 0;
    //converter last char on display is "," we will remove it.
    if(result.value.slice(-1) == ","){
        result.setAttribute("value", result.value.slice(0,-1));
    }
    let lastCharHistory = history.value.slice(-1); 
    if((!isNaN(lastCharHistory) || lastCharHistory == "=") && !p_flag && !m_flag){
        //set history for equal (inspired from google calc)
        if(!f_flag){
            history.setAttribute("value", result.value + "=");
            equalHistory();
        }
        else{
            //function
            // history.value = XXX,X c XXX,X
            // result.value = XXX,X
            // lastC = +;-;*;/;...
            var numberFromHistory = document.getElementsByTagName('button')[1];
            var charFromHistory = document.getElementsByTagName('a')[0].innerText;
            history.setAttribute("value", result.value + charFromHistory + numberFromHistory.innerText + "=");
            var n = history.value.split(charFromHistory);
            var num;
            if(n.length === 2){
                num = n[1].slice(0,-1);
            }
            else if(charFromHistory == "-" && n.length === 3){
                num = n[2].slice(0,-1);
            }
            else if(charFromHistory == "-" && n.length === 4){
                num = n[3].slice(0,-1);
            }
            //alert(charFromHistory+" FC "+history.value +" #"+n.length + "  n0: " + n[0] + "  n1: " + n[1] + "  n2: " + n[2] + "  n3: " + n[3]);
            var trures = getResult(history.value,num,charFromHistory);
            result.setAttribute("value", trures);
            functionHistory(charFromHistory);
        }
    }
    else if(!eq_flag){
        //function
        // history.value = XXX,X c XXX,X
        // result.value = XXX,X
        // lastC = +;-;*;/;...
        if(history.value.search("=") == -1){
            var trures = getResult(history.value,result.value,lastCharHistory);
            history.setAttribute("value", history.value + result.value + "=");
            result.setAttribute("value", trures);
            functionHistory(lastCharHistory);
        }else{
            var numberFromHistory = document.getElementsByTagName('button')[2];
            var charFromHistory = document.getElementsByTagName('a')[0].innerText;
            history.setAttribute("value", numberFromHistory.innerText + charFromHistory + result.value + "=");
            var n = history.value.split(charFromHistory);
            var num;
            if(n.length === 2){
                num = n[1].slice(0,-1);
            }
            else if(charFromHistory == "-" && n.length === 3){
                num = n[2].slice(0,-1);
            }
            else if(charFromHistory == "-" && n.length === 4){
                num = n[3].slice(0,-1);
            }
            //alert(charFromHistory+" FC "+history.value +" #"+n.length + "  n0: " + n[0] + "  n1: " + n[1] + "  n2: " + n[2] + "  n3: " + n[3]);
            var trures = getResult(history.value,num,charFromHistory);
            result.setAttribute("value", trures);
            functionHistory(charFromHistory);
        }
    }
    //implemented for addNumber() function
    f_flag = true;
    eq_flag = true;
    p_flag = false;
    m_flag = false;
}

/**
* functionHistory
* @brief Function for displaying result to history section.
* @description Function is called in every calculating function. The function works with values on display.
* @post The result appears in the history.
* @param {string} c Character that represents which function took place
* @returns {void}
*/

function functionHistory(c){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var n = history.value.split(c);
    if(n.length === 2){
        n[1] = n[1].slice(0,-1);
    }
    else if(c == "-" && n.length === 3){
        n[0] = "-"+n[1];
        n[1] = n[2].slice(0,-1);
    }
    else if(c == "-" && n.length === 4){
        n[0] = "-"+n[1];
        n[1] = "-"+n[3].slice(0,-1);
    }
    //creating a node: <div><btn> formula </btn> = <btn> result </btn></div>
    //TODO add styles(classes)
    var node = document.createElement("div");
    //Added for styling
    node.classList.add("historyDiv");
    var formula1btn = document.createElement("button");
    var formula2btn = document.createElement("button");
    var resultbtn = document.createElement("button");
    var functionA = document.createElement("a");
    //filling with same result because its equal function, other functions will have diferent results
    var form1textnode = document.createTextNode(n[0]);
    var form2textnode = document.createTextNode(n[1]);
    var restextnode = document.createTextNode(result.value);
    var equalnode = document.createTextNode("=");
    var functionNode = document.createTextNode(c);
    //adding class for function styling
    formula1btn.classList.add("historyBtn");
    formula2btn.classList.add("historyBtn");
    resultbtn.classList.add("historyBtn");
    //adding attribute for call function loadHistory()
    formula1btn.setAttribute("onclick","loadHistory(this.innerText);");
    formula2btn.setAttribute("onclick","loadHistory(this.innerText);");
    resultbtn.setAttribute("onclick","loadHistory(this.innerText);");
    formula1btn.appendChild(form1textnode);
    formula2btn.appendChild(form2textnode);
    resultbtn.appendChild(restextnode);
    functionA.appendChild(functionNode);
    node.appendChild(formula1btn);
    node.appendChild(functionA);
    node.appendChild(formula2btn);
    node.appendChild(equalnode);
    node.appendChild(resultbtn);
    document.getElementById("historyDropdown").prepend(node)
}

/**
* equalHistory
* @brief Function for displaying result to history section.
* @description Function is called in equal function. The function works with values on display.
* @post The result appears in the history.
* @returns {void}
*/

function equalHistory(){
    var result = document.getElementById('main_input');
    //creating a node: <div><btn> formula </btn> = <btn> result </btn></div>
    //TODO add styles(classes)
    var node = document.createElement("div");
    //Added for styling
    node.classList.add("historyDiv");
    var formulabtn = document.createElement("button");
    var resultbtn = document.createElement("button");
    //filling with same result because its equal function, other functions will have diferent results
    var formtextnode = document.createTextNode(result.value);
    var restextnode = document.createTextNode(result.value);
    var equalnode = document.createTextNode("=");
    //adding class for function styling
    formulabtn.classList.add("historyBtn");
    resultbtn.classList.add("historyBtn");
    //adding attribute for call function loadHistory()
    formulabtn.setAttribute("onclick","loadHistory(this.innerText);");
    resultbtn.setAttribute("onclick","loadHistory(this.innerText);");
    formulabtn.appendChild(formtextnode);
    resultbtn.appendChild(restextnode);
    node.appendChild(formulabtn);
    node.appendChild(equalnode);
    node.appendChild(resultbtn);
    document.getElementById("historyDropdown").prepend(node)
}

/**
* getResult
* @brief Function for caling functons in math_lib.js
* @description Function is called in every calculating function. The function caling function in math_lib.js to calculate result.
* @pre Some calculating function has to be called.
* @param {string} hist - String of first number
* @param {string} resu - String of second number 
* @param {string} c - Character that represents which function took place
* @returns {string} - Result value in string
*/

function getResult(hist,resu,c){
    var n = hist.split(c);
    //alert("n0: " + n[0] + "  n1: " + n[1] + "  n2: " + n[2] + "  n3: " + n[3] + "  resu:" + resu);
    if(n.length === 2){
        n[0].replace(",",".");
        n[1].replace(",",".");
    }
    else if(c == "-" && n.length === 3){
        n[0] = "-"+n[1].replace(",",".");
        n[1] = n[2].replace(",",".");
    }
    else if(c == "-" && n.length === 4){
        n[0] = "-"+n[1].replace(",",".");
        n[1] = "-"+n[3].replace(",",".");
    }
    //alert("n0: " + n[0] + "  n1: " + n[1] + "  n2: " + n[2] + "  n3: " + n[3] + "  resu:" + resu);
    //n.replace(",",".");
    resu.replace(",",".");
    switch(c){
        case "+": var res = add(Number(resu.replace(",",".")),Number(n[0].replace(",","."))).toString();
            break;
        case "-": 
        //alert("n0: " + n[0] + " resu: " + resu);
            var res = sub(Number(n[0].replace(",",".")),Number(resu.replace(",","."))).toString();
            break;
        case "*": var res = mul(Number(resu.replace(",",".")),Number(n[0].replace(",","."))).toString();
            break;
        case "/": var res = div(Number(n[0].replace(",",".")),Number(resu.replace(",","."))).toString();
            break;
        case "^": var res = pow(Number(n[0].replace(",",".")),Number(resu.replace(",","."))).toString();
            break;
        case "_": var res = sqrt(Number(n[0].replace(",",".")),Number(resu.replace(",","."))).toString();
            break;
        default: var res = "err";
            break;
    }
    var trures = res.replace(".",",");
    return trures;
}

/**
* loadHistory
* @returns {void}
*/

function loadHistory(x) {
    var result = document.getElementById('main_input');
    result.setAttribute("value", x);
    f_flag = false;
}

/**
* plusMinus
* @brief Function to change the sign.
* @description Function is called after pressing button +/-. The function works with values on display.
* @post The result appears on teh display.
* @returns {void}
*/

function plusMinus(){
    var result = document.getElementById('main_input');
    var n = result.value.search("-");    
    if(n == "-1"){
        var lenghtR = 0;
        document.getElementById('main_input').setAttribute("value",[result.value.slice(0, lenghtR), "-", result.value.slice(lenghtR)].join(''));
    }else{
        var n = result.value.replace("-","");
        document.getElementById('main_input').setAttribute("value",  n);
    }
}

/**
* delete character
* @brief Function 1 character on display.
* @description Function is called after pressing button C. The function works with values on display.
* @post The result appears on teh display.
* @returns {void}
*/

function deleteC(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value != "0"){
        if(result.value.length == 1){
            result.setAttribute("value", "0");
        }else{
            var n = result.value.slice(0,-1);
            result.setAttribute("value", n);
        }
    }else{
        if(history.value != "0"){
            history.setAttribute("value", "");
        }
    }
}

/**
* percentageF
* @brief Function to calculate 1 percent.
* @description Function is called after pressing button %. The function works with values on display.
* @post The result appears on the display.
* @returns {void}
*/

function percentageF(){
    last(fnc,0);
    fnc = 0;
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    if(result.value != "0"){
        var in1 = result.value.replace(",",".");
        var res = (Number(in1) / 100).toString();
        var trures = res.replace(".",",");
        history.setAttribute("value",result.value + "/100=");
        result.setAttribute("value",trures);
    }
    lastEq = false;
    
}

/**
* piF
* @brief Function that display pi.
* @description Function is called after pressing button pi. The function only show pi on the display.
* @post The result appears on the display.
* @returns {void}
*/

function piF(){
    document.getElementById('main_input').setAttribute("value", Math.PI);
    lastEq = false;
}

/**
* eF
* @brief Function that display e.
* @description Function is called after pressing button e. The function only show e on the display.
* @post The result appears on the display.
* @returns {void}
*/

function eF(){
    document.getElementById('main_input').setAttribute("value", Math.E);
    lastEq = false;
}

/**
* divide100
* @brief Function to calculate inverse value.
* @description Function is called after pressing button 1/x. The function works with values on display.
* @post The result appears on the display.
* @returns {void}
*/

function divide100(){
    var result = document.getElementById('main_input');
    var history = document.getElementById('last_input');
    var val = result.value.replace(",",".");
    res = (1 / Number(val)).toString();
    trures = res.replace(".",","); 
    history.setAttribute("value", "1/" + result.value + "=");
    result.setAttribute("value", trures);
    lastEq = false;
}

/**
* flagsToTrue
* @brief Function changes value of variables.
* @description Function is called in most clculating functions. Function changes value of variables.
* @returns {void}
*/

function flagsToTrue(){
    p_flag = true;
    m_flag = true;
    t_flag = true;
    d_flag = true;
    e_flag = true;
    s_flag = true;
}