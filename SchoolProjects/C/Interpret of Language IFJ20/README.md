### Author: Daniel Olearčin
### Project: Interpret of Language IFJ20
  - Makefile is used for translation
  - parser.c = Parser is controlling if code in IFJ20 have good semantics and syntactics
  - scanner.c = Scanner is reading IFJ20 code and creating tokens that represents each semantic structure and sending tokens to parser
  - stack.c = Stack implementation
  - str.c = Helping functions for operations with strings
  - symtable.c = Symtable implementation where are stored tokens
  - ifj20.c = Starting file where are used parser,scanner,stack,str,symtable.c files
