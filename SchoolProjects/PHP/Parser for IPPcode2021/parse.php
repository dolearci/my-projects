<?php

//error codes
define("parameters_error",10);
define("inputfile_error",11);
define("outpufile_error",12);
define("header_error",21);
define("opcode_error",22);
define("synlex_error",23);
ini_set('display_errors','stderr');


/* Functions */
// type check
function type_check($type)
{
    if(!preg_match("/^(int|bool|string|INT|BOOL|STRING)$/", $type))
        return false;
    return true;
}
// symbol check
// returns type of symbol
function symbol_check($symbol)
{
    if(var_check($symbol))
    {
        return "var";
    }
    elseif(preg_match("/^nil@(nil|NIL)$/", $symbol))
    {
        return "nil";
    }
    elseif(preg_match("/^int@[+\-]?\d+$/", $symbol))
    {
        return "int";
    }
    elseif(preg_match("/^bool@(true|false|TRUE|FALSE)$/", $symbol))
    {
        return 'bool';
    }
    elseif(preg_match("/^string@([^\s\\#]|(\\\d{3}))*$/", $symbol))
    {
        return "string";
    }
    else
    {
        return false;
    }
}
// var check
function var_check($var)
{
    if(!preg_match("/^(GF|LF|TF)@[a-zA-Z_\-$&%*!?][\w\-$&%*!?]*$/", $var))
        return false;
    return true;
}
// label check
function label_check($label)
{
    if(!preg_match("/^[a-zA-Z_\-$&%*!?][\w\-$&%*!?]*$/", $label))
        return false;
    return true;
}
function xml($array,$order,$opcodeval)
{
    echo("\t<instruction order=\"$order\" opcode=\"$array[0]\">\n");
    for($i = 1; $i < count($array); $i++)
    {
        //removing string@, int@ ... from var or symbols
        switch($array[$i][0])
        {
            case "i":
                $array[$i] = preg_replace("/^int@/","", $array[$i]);
                break;
            case "s": 
                $array[$i] = preg_replace("/^string@/","", $array[$i]);
                break;
            case "n":
                $array[$i] = preg_replace("/^nil@/","", $array[$i]);
                break;
            case "b":
                $array[$i] = preg_replace("/^bool@/","", $array[$i]);
                break;
            default:
                break;
        }
        //writing right arguments
        echo("\t\t<arg$i type=\"$opcodeval[$i]\">$array[$i]</arg$i>\n");
    }
    echo("\t</instruction>\n");
}
//Controlling arguments
if ($argc == 2)
{
    if ($argv[1] == "--help")
    {
        echo("Skript typu filtr (parse.php v jazyce PHP 7.4) načte ze standardního vstupu zdrojový kód v IPPcode21,
zkontroluje lexikální a syntaktickou správnost kódu
a vypíše na standardní výstup XML reprezentaci programu dle specifikace\n");
        exit(0);
    }
    else
        exit(parameters_error);
}
else if($argc != 1)
    exit(parameters_error);


//Header check
$line = trim(fgets(STDIN));
if($line != ".IPPcode21")
    exit(header_error);
$order = 0;
$opcodeval = array(
    1 => "",
    2 => "",
    3 => "",
    4 => "",
);
echo("<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n");
echo("<program language=\"IPPcode21\">\n");
//File check
while($line = fgets(STDIN))
{   
    if($line == "\r\n" || $line == "\n") // TODO zistit ci \n alebo \r\n
        continue;
    $line = trim(preg_replace("/#.*$/","",$line)); // removes # and everything after that
    if($line == "") //when whole line is one comment
        continue;
    $order = $order + 1;
    $split = explode(' ', $line);
    switch(strtoupper($split[0]))
    {
        case "BREAK":
        case "CREATEFRAME":
        case "PUSHFRAME":
        case "POPFRAME":
        case "RETURN":
            if(count($split) != 1)
                exit(synlex_error);
            else
                xml($split,$order,$opcodeval);
            break; // nothing
        case "DEFVAR":
        case "POPS":
            if(count($split) != 2)
                exit(synlex_error);
            else if(var_check($split[1]))
            {
                $opcodeval[1] = "var";
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1VAR
        case "PUSHS":
        case "WRITE":
        case "EXIT":
        case "DPRINT":
            if(count($split) != 2)
                exit(synlex_error);
            else if(symbol_check($split[1]) != false)
            {
                $opcodeval[1] = symbol_check($split[1]);
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1SYMB
        case "LABEL":
        case "JUMP":
        case "CALL":
            if(count($split) != 2)
                exit(synlex_error);
            else if(label_check($split[1]))
            {
                $opcodeval[1] = "label";
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1LABEL
        case "ADD":
        case "SUB":
        case "MUL":
        case "IDIV":
        case "LT":
        case "GT":
        case "EQ":
        case "AND":
        case "OR":
        case "NOT":
        case "GETCHAR":
        case "SETCHAR":
        case "CONCAT":
        case "STRI2INT":
            if(count($split) != 4)
                exit(synlex_error);
            else if(var_check($split[1]) && symbol_check($split[2]) && symbol_check($split[3]))
            {
                $opcodeval[1] = "var";
                $opcodeval[2] = symbol_check($split[2]);
                $opcodeval[3] = symbol_check($split[3]);
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1VAR1SYMB1SYMB
        case "MOVE":
        case "INT2CHAR":
        case "STRLEN":
        case "TYPE":
            if(count($split) != 3)
                exit(synlex_error);
            else if(var_check($split[1]) && symbol_check($split[2]))
            {
                $opcodeval[1] = "var";
                $opcodeval[2] = symbol_check($split[2]);
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1VAR1SYMB
        case "READ":
            if(count($split) != 3)
                exit(synlex_error);
            else if(var_check($split[1]) && type_check($split[2]))
            {
                $opcodeval[1] = "var";
                $opcodeval[2] = "type";
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1VAR1TYPE
        case "JUMPIFEQ":
        case "JUMPIFNEQ":
            if(count($split) != 4)
                exit(synlex_error);
            else if(label_check($split[1]) && symbol_check($split[2]) && symbol_check($split[2]))
            {
                $opcodeval[1] = "label";
                $opcodeval[2] = symbol_check($split[2]);
                $opcodeval[3] = symbol_check($split[3]);
                xml($split,$order,$opcodeval);
            }
            else    
                exit(synlex_error);
            break; // 1LABEL1SYMB1SYMB
        default:
            exit(opcode_error);
    }
}
echo("</program>\n");
exit(0);
?>
