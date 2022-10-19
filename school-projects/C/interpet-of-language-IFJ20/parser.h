/*
 * File:   parser.h
 * Author: Adam
 *
 * Created on 28. novembra 2019, 13:19
 */

#ifndef _PARSER_H
#define _PARSER_H

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "symtable.h"

#define load_token()   ParserToken = getNextToken()

#define LOAD_TOKEN_AND_CHECK()										\
	load_token();													\


typedef struct {    
    tHTable funkceTS;
    tHTable localTS;
} parserData;

bool func_declaration();
bool parameters();
bool type();
bool packages();
bool def_func();
bool packages_n();
bool parameters_n();
bool types();
bool type_n();
bool commands();
bool command();
bool body();
bool id_n();
bool term();
bool assign();
bool assign_right();
bool command_id();
bool definition();
char* getTokenString(Token);
char* getKeyword(Token);
void debug_tables();
void use_init(tHTable*);
void use_insert(tHTable*, tKey, tData);
void htPrintTable(tHTable*);
char* getDataType(tType);


#endif
