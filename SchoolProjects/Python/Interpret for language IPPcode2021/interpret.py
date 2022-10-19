import os, sys, re, argparse
import xml.etree.ElementTree as ET
from xml.etree.ElementTree import fromstring, ElementTree
class arg:
    def __init__(self,arg_type,arg_value):
        self.type = arg_type
        self.value = arg_value
class instruction:
    def __init__(self,name,number):
        self.name = name
        self.number = number
        self.args = []
    def add_arg(self,arg):
        self.args.append(arg)
        
    
def args_check(args="help source= input="):
    ap = argparse.ArgumentParser(allow_abbrev=False)
    ap.add_argument("--source", type=argparse.FileType("r"))
    ap.add_argument("--input", type=argparse.FileType("r"))
    args = ap.parse_args()
    input_file = ""
    source = ""
    if args.source and args.input:
        source = args.source.read()
        input_file = args.input.read()
    elif args.source:
        source = args.source.read()
    elif args.input:
        source = sys.stdin.read()
        input_file = args.input.read()
    else:
        print("Argument missing")
        sys.exit(10)
    return source,input_file

def language_check(root):
    instructions = []
    for name in root.attrib:
         lng = name
         break;
    if root.tag != "program" or not("language" in root.attrib) or not("IPPcode21" in root.attrib[lng]):
        print("bad xml file")
        sys.exit(32)
    for child in root:
        if child.tag != "instruction":
            print("bad xml file")
            sys.exit(32)
        child_list = list(child.attrib.keys())
        if not("order" in child_list) or not("opcode" in child_list):
            print("bad xml file")
            sys.exit(32)
        child_items = list(child.attrib.items())
        new_instruction = instruction(child_items[1][1],child_items[0][1])
        for sub in child:
            if not(re.match("arg[123]",sub.tag)):
                print("bad xml file")
                sys.exit(32)
            sub_items = list(sub.attrib.items())
            new_arg = arg(sub_items[0][1],sub.text)
            new_instruction.add_arg(new_arg)
        instructions.append(new_instruction)
    return instructions

def isnumber(n):
    try:
        float(n)   
    except ValueError:
        return False
    return True

def check_type(arg):
    if arg.type != "type":
            sys.exit(32)
    if not(arg.value == 'int' or arg.value == 'string' or arg.value == 'bool' or arg.value == 'INT' or arg.value == 'STRING' or arg.value == 'BOOL'):
        sys.exit(53)
    return True

def check_bool(arg):
    if arg.type != "bool":
            sys.exit(32)
    if not(arg.value == 'true' or arg.value == 'false' or arg.value == 'TRUE' or arg.value == 'FALSE'):
        sys.exit(53)
    return True

def check_label(arg,exist,labels,order):
    if arg.type != "label":
        sys.exit(32)
    if exist == "1":
        if not(arg.value in labels):
            sys.exit(52)
    else:
        if arg.value in labels:
            sys.exit(52)
        labels[arg.value] = order
    return labels

def check_var(arg,exist,TF,GF,LF):
    if arg.type != "var":
        sys.exit(32)
    if exist == "1":
        try:
            frame, value = arg.value.split('@')
        except:
            sys.exit(32)
        if frame == "GF":
            if not(value in GF):
                sys.exit(52)
        elif frame == "LF":
            if LF[0] == "0" or not(value in LF[-1]):
                sys.exit(52)
        if frame == "TF":
            if not(value in TF):
                sys.exit(52)
    else:
        try :
            frame, value = arg.value.split('@')
        except:
            sys.exit(32)
        rgx = r"^[a-zA-Z_$][a-zA-Z_$0-9]*$"
        match = re.search(rgx, value)
        if match is None:
            sys.exit(32)
        if frame == "GF":
            if value in GF:
                sys.exit(52)
            GF.append(value)
        elif frame == "LF":
            if value in LF:
                sys.exit(52)
            if LF[0] == "0":
                sys.exit(55)
            LF.append(value)
        if frame == "TF":
            if value in TF:
                sys.exit(52)
            if TF[0] == "0":
                sys.exit(55)
            TF.append(value)
    return TF,GF,LF

def symbol_return(value):
    if isnumber(value):
        return "int"
    elif value == 'true' or value == 'false' or value == 'TRUE' or value == 'FALSE':
        return "bool"
    elif value == "nil":
        return "nil"
    else:
        return "string"
    
def symbol_check(arg,TF,GF,LF):
    if arg.type == "var":
        check_var(arg,"1",TF,GF,LF)
        return "var"
    elif arg.type == "string":
        return "string"
    elif arg.type == "int":
        if not(isnumber(arg.value)):
            sys.exit(53)
        return "int"
    elif arg.type == "bool":
        if not(check_bool(arg)):
            sys.exit(53)
        return "bool"
    elif arg.type == "nil":
        if not(arg.value == "nil"):
            sys.exit(53)
        return "nil"
    else:
        sys.exit(32)
    
def args_cnt(nmbr_of_args,ins):
    if nmbr_of_args == 0:
        if len(ins.args) != 0:
            sys.exit(32)
        return
    if nmbr_of_args == 1:
        if len(ins.args) != 1:
            sys.exit(32)
        return
    elif nmbr_of_args == 2:
        if len(ins.args) != 2:
            sys.exit(32)
        return
    if nmbr_of_args == 3:
        if len(ins.args) != 3:
            sys.exit(32)
        return
    else:
        sys.exit(32)

def insert(arg1,arg2,TF,GF,LF,real_frames):
    try:
        if arg2.type == "var":
            for item in real_frames.items():
                if item[0] == arg2.value:
                    real_frames[arg1.value] = item[1]
        else:
            real_frames[arg1.value] = arg2.value
        return real_frames
    except:
        real_frames[arg1.value] = arg2
        return real_frames

def code_to_asci(value):
    sqlist = re.findall(r'\\\d{3}', value)
    while(len(sqlist) > 0):
        escseq = sqlist[0]
        value = value.replace(escseq, chr(int(escseq.replace("\\", ""))))
        while(escseq in sqlist):
            sqlist.remove(escseq)
    return value

def semantics_check(instructions,inpt):
    GF = []
    TF = []
    LF = []
    stack = []
    real_frames = dict()
    TF.append("0")
    LF.append("0")
    labels = dict()
    i = -1
    while i < len(instructions)-1:
        i = i + 1 
        ins = instructions[i]
        ins.name = ins.name.upper()
        if ins.name == "MOVE":
            args_cnt(2,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            symbol_check(ins.args[1],TF,GF,LF)
            real_frames = insert(ins.args[0],ins.args[1],TF,GF,LF,real_frames)
        elif ins.name == "CREATEFRAME":
            args_cnt(0,ins)
            TF.clear()
            TF.append("1")
        elif ins.name == "PUSHFRAME":
            args_cnt(0,ins)
            LF[0] = "1"
            LF.append(TF[1:])
            TF.clear()
            TF.append("0")
        elif ins.name == "POPFRAME":
            args_cnt(0,ins)
            if TF[0] == "0":
                sys.exit(55)
            TF[1:] = LF.pop()
            if len(LF) <= 1:
                LF[0] = "0"
        elif ins.name == "DEFVAR":
            args_cnt(1,ins)
            TF,GF,LF = check_var(ins.args[0],"0",TF,GF,LF)
        elif ins.name == "CALL":
            args_cnt(1,ins)
            labels = check_label(ins.args[0],"1",labels,0)
            i = int(labels[ins.args[0].value])
            stack.append(i)
        elif ins.name == "RETURN":
            args_cnt(0,ins)
        elif ins.name == "PUSHS":
            symbol_check(ins.args[0],TF,GF,LF)
            args_cnt(1,ins)
            if ins.args[0].type == "var":
                try:
                    value = real_frames[ins.args[0].value]
                    stack.append(value)
                except:
                    sys.exit(56)
            else:
                stack.append(ins.args[0].value)
        elif ins.name == "POPS":
            args_cnt(1,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if len(stack) < 1:
                sys.exit(56)
            real_frames = insert(ins.args[0],stack.pop(),TF,GF,LF,real_frames)
        elif ins.name == "ADD":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if (ins.args[1].type == "int" or ins.args[1].type == "var") and (ins.args[2].type == "int" or ins.args[2].type == "var"):
                if ins.args[1].type == "var":
                    try:
                        value1 = real_frames[ins.args[1].value]
                    except:
                        sys.exit(56)
                else:
                    value1 = ins.args[1].value
                if ins.args[2].type == "var":
                    try:
                        value2 = real_frames[ins.args[2].value]
                    except:
                        sys.exit(56)
                else:
                    value2 = ins.args[2].value
                if not(isnumber(value1) and isnumber(value2)):
                       sys.exit(53)
                value = int(value1) + int(value2)
                real_frames[ins.args[0].value] = value
            else:
                sys.exit(53)
        elif ins.name == "SUB":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if (ins.args[1].type == "int" or ins.args[1].type == "var") and (ins.args[2].type == "int" or ins.args[2].type == "var"):
                if ins.args[1].type == "var":
                    try:
                        value1 = real_frames[ins.args[1].value]
                    except:
                        sys.exit(56)
                else:
                    value1 = ins.args[1].value
                if ins.args[2].type == "var":
                    try:
                        value2 = real_frames[ins.args[2].value]
                    except:
                        sys.exit(56)
                else:
                    value2 = ins.args[2].value
                if not(isnumber(value1) and isnumber(value2)):
                       sys.exit(53)
                value = int(value1) - int(value2)
                real_frames[ins.args[0].value] = value
        elif ins.name == "MUL":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if (ins.args[1].type == "int" or ins.args[1].type == "var") and (ins.args[2].type == "int" or ins.args[2].type == "var"):
                if ins.args[1].type == "var":
                    try:
                        value1 = real_frames[ins.args[1].value]
                    except:
                        sys.exit(56)
                else:
                    value1 = ins.args[1].value
                if ins.args[2].type == "var":
                    try:
                        value2 = real_frames[ins.args[2].value]
                    except:
                        sys.exit(56)
                else:
                    value2 = ins.args[2].value
                if not(isnumber(value1) and isnumber(value2)):
                       sys.exit(53)
                value = int(value1) * int(value2)
                real_frames[ins.args[0].value] = value
        elif ins.name == "IDIV":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if (ins.args[1].type == "int" or ins.args[1].type == "var") and (ins.args[2].type == "int" or ins.args[2].type == "var"):
                if ins.args[1].type == "var":
                    try:
                        value1 = real_frames[ins.args[1].value]
                    except:
                        sys.exit(56)
                else:
                    value1 = ins.args[1].value
                if ins.args[2].type == "var":
                    try:
                        value2 = real_frames[ins.args[2].value]
                    except:
                        sys.exit(56)
                else:
                    value2 = ins.args[2].value
                if not(isnumber(value1) and isnumber(value2)):
                       sys.exit(53)
                if int(value2) == 0:
                    sys.exit(57)
                value = int(value1) // int(value2)
                real_frames[ins.args[0].value] = value
        elif ins.name == "LT" or ins.name == "GT" or ins.name == "EQ":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == symbol2:
                if ins.name == "LT":
                    if value1 < value2:
                        real_frames[ins.args[0].value] = "true"
                    else:
                        real_frames[ins.args[0].value] = "false"
                elif ins.name == "GT":
                    if value1 > value2:
                        real_frames[ins.args[0].value] = "true"
                    else:
                        real_frames[ins.args[0].value] = "false"
                elif ins.name == "EQ":
                    if value1 == value2:
                        real_frames[ins.args[0].value] = "true"
                    else:
                        real_frames[ins.args[0].value] = "false"
            else:
                sys.exit(53)
        elif ins.name == "AND" or ins.name == "OR":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == "bool" and symbol2 == "bool":
                if ins.name == "AND":
                    if value1 == "true" and value2 == "true":
                        real_frames[ins.args[0].value] = "true"
                    else:
                        real_frames[ins.args[0].value] = "false"
                else:
                    if value1 == "true" or value2 == "true":
                        real_frames[ins.args[0].value] = "true"
                    else:
                        real_frames[ins.args[0].value] = "false"
            else:
                sys.exit(53)
        elif ins.name == "NOT":
            args_cnt(2,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol1 == "bool":
                if value == "true": 
                    real_frames[ins.args[0].value] = "false"
                else:
                    real_frames[ins.args[0].value] = "true"
            else:
                sys.exit(53)
        elif ins.name == "INT2CHAR":
            args_cnt(2,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol1 == "int":
                try:
                    res = chr(int(value1))
                except:
                    sys.exit(58)
                real_frames[ins.args[0].value] = res
            else:
                sys.exit(53)
        elif ins.name == "STRI2INT":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == "string" and symbol2 == "int":
                try:
                    res = ord(value1[int(value2)])
                except:
                    sys.exit(58)
                real_frames[ins.args[0].value] = res
            else:
                sys.exit(53)
        elif ins.name == "READ":
            args_cnt(2,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if check_type(ins.args[1]):
                if inpt:
                    x = inpt.pop()
                else:
                    x = input()
                real_frames[ins.args[0].value] = x
            else:
                real_frames[ins.args[0].value] = "nil"
        elif ins.name == "WRITE":
            args_cnt(1,ins)
            symbol_check(ins.args[0],TF,GF,LF)
            if not(ins.args[0].value == "nil"):
                if ins.args[0].type == "var":
                    try:
                        value = real_frames[ins.args[0].value]
                        symbol = symbol_return(value)
                        if symbol == "string":
                            value = code_to_asci(value)
                        print(value,end="")
                    except:
                        sys.exit(56)
                else:
                    value = ins.args[0].value
                    if ins.args[0].type == "string":
                        value = code_to_asci(value)
                    print(value,end="")
        elif ins.name == "CONCAT":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == "string" and symbol2 == "string":
                res = value1 + value2
                real_frames[ins.args[0].value] = res
            else:
                sys.exit(53)
        elif ins.name == "STRLEN":
            args_cnt(2,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol1 ==  "string":
                res = len(value1)
                real_frames[ins.args[0].value] = res
            else:
                sys.exit(53)
        elif ins.name == "GETCHAR":
            args_cnt(3,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 ==  "string" and symbol2 == "int":
                try:
                    res = value1[int(value2)]
                    real_frames[ins.args[0].value] = res
                except:
                    sys.exit(58)
            else:
                sys.exit(53)
        elif ins.name == "SETCHAR":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == "int" and symbol2 == "string":
                try:
                    res = real_frames[ins.args[0].value]
                    if int(value1) > len(res) or len(value2) == 0:
                        sys.exit(58)
                    res = res[:int(value1)] + value2[0] + res[int(value1)+1:]
                    real_frames[ins.args[0].value] = res
                except:
                    sys.exit(56)
            else:
                sys.exit(53)
        elif ins.name == "TYPE":
            args_cnt(2,ins)
            TF,GF,LF = check_var(ins.args[0],"1",TF,GF,LF)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            if symbol1 == "nil":
                real_frames[ins.args[0].value] = "nil"
            elif symbol1 == "string":
                real_frames[ins.args[0].value] = "string"
            elif symbol1 == "int":
                real_frames[ins.args[0].value] = "int"
            elif symbol1 == "bool":
                real_frames[ins.args[0].value] = "bool"
            else:
                real_frames[ins.args[0].value] = ""
        elif ins.name == "LABEL":
            args_cnt(1,ins)
            labels = check_label(ins.args[0],"0",labels,ins.number)
        elif ins.name == "JUMP":
            args_cnt(1,ins)
            labels = check_label(ins.args[0],"1",labels,0)
            i = int(labels[ins.args[0].value])
        elif ins.name == "JUMPIFEQ":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            labels = check_label(ins.args[0],"1",labels,0)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == symbol2 or symbol1 == "nil" or symbol2 == "nil":
                if value1 == value2:
                    i = int(labels[ins.args[0].value])
            else:
                sys.exit(53)
        elif ins.name == "JUMPIFNEQ":
            args_cnt(3,ins)
            symbol1 = symbol_check(ins.args[1],TF,GF,LF)
            symbol2 = symbol_check(ins.args[2],TF,GF,LF)
            labels = check_label(ins.args[0],"1",labels,0)
            if symbol1 == "var":
                try:
                    value1 = real_frames[ins.args[1].value]
                    symbol1 = symbol_return(value1)
                except:
                    sys.exit(56)
            else:
                value1 = ins.args[1].value
            if symbol2 == "var":
                try:
                    value2 = real_frames[ins.args[2].value]
                    symbol2 = symbol_return(value2)
                except:
                    sys.exit(56)
            else:
                value2 = ins.args[2].value
            if symbol1 == symbol2 or symbol1 == "nil" or symbol2 == "nil":
                if value1 != value2:
                    i = int(labels[ins.args[0].value])
            else:
                sys.exit(53)
        elif ins.name == "EXIT":
            args_cnt(1,ins)
            symbol_check(ins.args[0],TF,GF,LF)
            if ins.args[0].type == "int" and isnumber(ins.args[0].value) and (0 <= int(ins.args[0].value) <= 50):
                print(ins.args[0].value)
                sys.exit(0)
            else:
                sys.exit(57)
        elif ins.name == "DPRINT":
            args_cnt(1,ins)
            symbol_check(ins.args[0],TF,GF,LF)
        elif ins.name == "BREAK":
            args_cnt(0,ins)
        else:
            sys.exit(53)
    

src, inpt = args_check()
tree = ElementTree(fromstring(src))
instructions = language_check(tree.getroot())
semantics_check(instructions,inpt)
sys.exit(0)

    
