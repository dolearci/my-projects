var MathLib = require('./math_lib');
var expect = require('chai').expect;

describe('MathLib Test', function(){
    it('has a module', function(){
        expect(MathLib).to.be.ok;
    });

    describe('add', function(){
        it('adds two numbers togather', function(){
            expect(MathLib.add(1,1)).to.equal(2);
            expect(MathLib.add(1,-1)).to.equal(0);
        });
    });

    describe('sub', function(){
        it('subtract two numbers from each other', function(){
            expect(MathLib.sub(1,1)).to.equal(0);
            expect(MathLib.sub(1,-1)).to.equal(2);
        });
    });

    describe('mul', function(){
        it('multiply two numbers', function(){
            expect(MathLib.mul(1,1)).to.equal(1);
            expect(MathLib.mul(2,0)).to.equal(0);
            expect(MathLib.mul(0,2)).to.equal(0);
        });
    });

    describe('div', function(){
        it('divide two numbers', function(){
            expect(MathLib.div(1,1)).to.equal(1);
            expect(MathLib.div(5,2)).to.equal(2.5);
            expect(MathLib.div(1,4)).to.equal(0.25);
        });
    });

    describe('fact', function(){
        it('factorial of one number', function(){
            expect(MathLib.fact(1)).to.equal(1);
            expect(MathLib.fact(5)).to.equal(120);
            expect(MathLib.fact(11)).to.equal(39916800);
        });
    });

    describe('pow', function(){
        it('power of two numbers', function(){
            expect(MathLib.pow(1,25)).to.equal(1);
            expect(MathLib.pow(25,0)).to.equal(1);
            expect(MathLib.pow(1.8,3)).to.equal(5.832);
        });
    });

    //Doporučuju udělat pomocnou funkci která změní číslo na zlomek a pak použít funkci pow
    describe('sqrt', function(){
        it('square root of two numbers', function(){
            expect(MathLib.sqrt(0,5)).to.equal(0);
            expect(MathLib.sqrt(1,5)).to.equal(1);
            expect(MathLib.sqrt(16,2)).to.equal(4);
            expect(MathLib.sqrt(125,3)).to.equal(5);
        });
    });

    describe('log', function(){
        it('logarithm first number is base, second number is argument', function(){
            expect(MathLib.log(3,1)).to.equal(0);
            expect(MathLib.log(5,1)).to.equal(0);
            expect(MathLib.log(2,2)).to.equal(1);
            expect(MathLib.log(6,6)).to.equal(1);
            expect(MathLib.log(10,100)).to.equal(2);          
        });
    });
});