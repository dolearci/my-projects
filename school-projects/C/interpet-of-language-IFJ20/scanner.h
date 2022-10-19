#ifndef _SCANNER_H
#define _SCANNER_H

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>
#include "str.h"
#include "stack.h"

typedef enum { //enum moznych error_code
  ALL_OK,
  LEX_ERROR,
  PREC_SYNTAX_ERROR,
  ALLOC_ERROR,
  RANDOM,
} Error_code;

typedef enum {
  Key_int,
  Key_float64,
  Key_else,
  Key_if,
  Key_for,
  Key_func,
  Key_package,
  Key_return,
  Key_string,
  Key_inputs,
  Key_inputi,
  Key_inputf,
  Key_len,
  Key_print,
  Key_int2float,
  Key_float2int,
  Key_substr,
  Key_ord,
  Key_chr,
} KeyWords;

typedef enum {
  Token_EOF,
  Token_EOL,
  
  Token_Keyword,
  Token_Identifier,

  Token_Plus,//             +
  Token_Minus,//            -
  Token_Multiply,//         *
  Token_Divide,//           /

  Token_Left_Bracket,//     (
  Token_Right_Bracket,//    )
  Token_Left_Brace,//       {
  Token_Right_Brace,//      }
  Token_Greater,//          >
  Token_Greater_Or_Equal,// >=
  Token_Lesser,//           <
  Token_Lesser_Or_Equal,//  <=
  Token_Equal,//            ==
  Token_Assign,//           =
  Token_Define_Type,//      :=
  Token_Not_Equal,//        !=

  Token_INT,
  Token_FLOAT,
  Token_STRING,
  Token_BOOL,
  Token_Comma,
  Token_Colon,
  Token_SemiColon,
  Token_Dot,
  
  Token_DokKom,//dokumentacni komentar

} TokenType;

typedef struct {
  int INT;
  string *STRING;
  KeyWords KEYWORD;
  bool BOOL;
  float FLOAT;
} TokenAttribute;

typedef struct {
  TokenType TokenType;
  TokenAttribute TokenAttribute;
} Token;


int setSourceFile(FILE *f);
Token getNextToken();
char CheckNextChar();
Token IdOrKeyword();
#endif
