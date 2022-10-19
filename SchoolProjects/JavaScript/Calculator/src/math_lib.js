/**
 * math_lib module
 * @module math_lib
 */

/**
 * @type {number}
 */
var result = 0

/**
 * @brief Rounding number to true value
 * 
 * @param {number} number - number
 * @returns {number} - rounded number
 */
function strip(number) 
{
    number = parseFloat(number).toPrecision(15)
    number = Math.round(number * 1000000000000000) / 1000000000000000
    return number
}

/**
 * @brief Method Adding
 * 
 * @param {number} a - first number
 * @param {number} b - number that will be added to first number
 * @returns {number} - result of method
 */
function add(a,b)
{
    result = a + b
    return strip(result)
}

/**
 * @brief Method Subtraction
 * 
 * @param {number} a - first number
 * @param {number} b - number that will be subtracted from first number
 * @returns {number} - result of method
 */
function sub(a,b)
{
    result = a - b
    return strip(result)
}

/**
 * @brief Method Multiply
 * 
 * @param {number} a - base
 * @param {number} b - multiplikator
 * @returns {number} - result of method
 */
function mul(a,b)
{
    result = a * b
    return strip(result)
}

/**
 * @brief Method Division
 * 
 * @param {number} a - divident
 * @param {number} b - divisor
 * @returns {number} - result of method
 */
function div(a,b)
{
    result = a / b
    return strip(result)
}

/**
 * @brief Method Factorial
 * 
 * @param {number} a - number to be factorised
 * @returns {number} - result of method
 */
function fact(a)
{
    result = 1
    if(a < 0)
        throw new Exception("factorised number must be higher or equal than 0")
    if(Math.round(a) != a)
        throw new Exception("number must be int")
    var i = 2
    for(i; i < a + 1; i++)
    {
        result = result * i
    }
    return strip(result)
}

/**
 * @brief Method Pow
 * 
 * @param {number} a - base
 * @param {number} b - power
 * @returns {number} - result of method
 */
function pow(a,b)
{
    result = Math.pow(a,b)
    return strip(result)
}

/**
 * @brief Method Logarithm
 * 
 * @param {number} a - base
 * @param {number} b - number
 * @returns {number} - result of method
 */
function log(a,b)
{
    if(a < 0)
        throw new Exception("number must be higher than 0")
    if(b < 0)
        throw new Exception("base must be higher than 0")
    result = Math.log(b) / Math.log(a)
    return strip(result)
}

/**
 * @brief Method Sqrt
 * 
 * @param {number} a - number
 * @param {number} b - base
 * @returns {number} - result of method
 */
function sqrt(a,b)
{
    if(a < 0 || b < 0)
        throw new Exception("base and number must be higher than 0")
    result = pow(a,1/b)
    return strip(result)
}

module.exports ={
    add: add,
    sub: sub,
    mul: mul,
    div: div,
    fact: fact,
    pow: pow,
    log: log,
    sqrt: sqrt
}
