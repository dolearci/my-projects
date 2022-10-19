const readline = require('readline')

const rl = readline.createInterface({
  input: process.stdin,
  output: process.stdout
})

var MathLib = require('../src/math_lib.js')
var priemer = 0
var odchylka = 0
var sum_v_odchylke = 0
let inputNumbers = [];
rl.on('line', function(line) {
  // Put the number in an array
  inputNumbers.push(parseInt(line));
  // Stop when the array is 10,100,1000 numbers long
  // numbers must be separated by enter (\n)
  if (inputNumbers.length == 10) {
      rl.close();
      length = inputNumbers.length
      counter = inputNumbers.length
      while(length != 0)
      {
          length = MathLib.sub(length,1)
          priemer = MathLib.add(priemer,inputNumbers[length])
          sum_v_odchylke = MathLib.add(sum_v_odchylke,MathLib.pow(inputNumbers[length],2))
      }       
      priemer = MathLib.div(priemer,counter)   
      sum_v_odchylke = MathLib.sub(sum_v_odchylke,MathLib.mul(counter,MathLib.pow(priemer,2)))
      sum_v_odchylke = MathLib.mul(MathLib.div(1,MathLib.sub(counter,1)),sum_v_odchylke)
      odchylka = MathLib.sqrt(sum_v_odchylke,2)
      console.log(odchylka)
  }
});
