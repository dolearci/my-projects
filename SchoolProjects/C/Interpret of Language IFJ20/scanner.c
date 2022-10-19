
#include "scanner.h"

FILE* Source; //promenna pro ulozeni souboru
tStack* Stack;
//flag prvniho tokenu na radku 1 => nacita se prvni token na radku 0 => prvni token na radku uz byl nacten
int FirstToken = 1;
int state = 0;
string* String;
int c;
int SpaceCounter = 0;
Token ScannerToken;
char CheckNextChar();

Token IdOrKeyword() {
    if (!strCmpConstStr(String, "int")) {
        ScannerToken.TokenAttribute.KEYWORD = Key_int;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "float64"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_float64;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "else"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_else;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!strCmpConstStr(String, "if")) {
        ScannerToken.TokenAttribute.KEYWORD = Key_if;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!strCmpConstStr(String, "for")) {
        ScannerToken.TokenAttribute.KEYWORD = Key_for;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "func"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_func;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "package"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_package;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "return"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_return;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "string"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_string;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "inputs"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_inputs;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "inputi"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_inputi;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "inputf"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_inputf;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "len"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_len;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "print"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_print;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "int2float"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_int2float;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "float2int"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_float2int;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "substr"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_substr;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "ord"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_ord;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else if (!(strCmpConstStr(String, "chr"))) {
        ScannerToken.TokenAttribute.KEYWORD = Key_chr;
        ScannerToken.TokenType = Token_Keyword;
        state = 0;
    }
    else {
        state = 0;
        ScannerToken.TokenType = Token_Identifier;
        ScannerToken.TokenAttribute.STRING = String;
    }
    return ScannerToken;
}

int setSourceFile(FILE* f) {
    Source = f;
    Stack = malloc(sizeof(tStack));
    if (Stack == NULL) {
        exit(99);
    }
    stackInit(Stack);
    stackPush(Stack, 0);
    return ALL_OK;
}

Token getNextToken() {
    String = malloc(sizeof(string));
    if (String == NULL) {
        exit(99);
    }
    strInit(String);
    // vymazeme obsah atributu a v pripade identifikatoru
    // budeme postupne do nej vkladat jeho nazev
    while (1) {
        // nacteni dalsiho znaku
        c = getc(Source);
        switch (state) {
            // S stav
        case 0:
            if (c == EOF) {
                ScannerToken.TokenType = Token_EOF;
                return ScannerToken;
            }
            else if (c == '_') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Identifier;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == 10) {
                state = 15;
            }
            else if (isspace(c)) {
                state = 0;
            }
            else if (c == '+') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Plus;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '-') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Minus;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '*') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Multiply;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '/') {
                if (CheckNextChar() == '/') {
                    state = 18;
                }
                else if (CheckNextChar() == '*') {
                    state = 19;
                }
                else {
                    strAddChar(String, c);
                    //state = 1;
                    ScannerToken.TokenType = Token_Divide;
                    ScannerToken.TokenAttribute.STRING = String;
                    return ScannerToken;
                }
            }
            else if (c == '(') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Left_Bracket;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == ')') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Right_Bracket;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '{') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Left_Brace;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '}') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Right_Brace;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }

            else if (c == '<') {
                strAddChar(String, c);
                state = 1;
            }
            else if (c == '>') {
                strAddChar(String, c);
                state = 2;
            }
            else if (c == '=') {
                strAddChar(String, c);
                state = 3;
            }
            else if (c == '!') {
                strAddChar(String, c);
                state = 4;
            }
            else if (c == ',') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Comma;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == ':') {
                strAddChar(String, c);
                state = 13;
            }
            else if (c == ';') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_SemiColon;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (c == '.') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Dot;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else if (isalpha(c)) {
                strAddChar(String, c);
                state = 5;
            }
            else if (isdigit(c) && c == '0') {
                strAddChar(String, c);
                state = 6;
            }
            else if (isdigit(c) && c != '0') {
                strAddChar(String, c);
                state = 12;
            }
            else if (c == 34) { // "  - komentar TODO
                state = 14;
            }
            else {
                exit(1);
            }
            break;

        case 1:
            if (c == '=') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Lesser_Or_Equal;
                ScannerToken.TokenAttribute.STRING = String;
                state = 0;
                return ScannerToken;
            }
            else {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_Lesser;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            break;

        case 2:
            if (c == '=') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Greater_Or_Equal;
                ScannerToken.TokenAttribute.STRING = String;
                state = 0;
                return ScannerToken;
            }
            else {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_Greater;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            break;

        case 3:
            if (c == '=') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Equal;
                ScannerToken.TokenAttribute.STRING = String;
                state = 0;
                return ScannerToken;
            }
            else {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_Assign;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            break;

        case 4:
            if (c == '=') {
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Not_Equal;
                ScannerToken.TokenAttribute.STRING = String;
                state = 0;
                return ScannerToken;
            }
            else exit(LEX_ERROR);
            break;

        case 5:
            if (isalnum(c) || c == '_') {
                strAddChar(String, c);
            }
            else {
                ungetc(c, Source);
                return IdOrKeyword();
            }
            break;

        case 6: //    0
            if (c == '0') {
                state = 6;
            }
            else if (c == '.') {
                strAddChar(String, c);
                state = 7;
            }
            else if (isspace(c) || c == EOF || c == 10 || c == '+' || c == '-' || c == '/' || c == '*' || '(' || ',' || ')' || ';' || ':') {
                ungetc(c, Source);
                ScannerToken.TokenType = Token_INT;
                ScannerToken.TokenAttribute.INT = atoi(String);
                state = 0;
                return ScannerToken;
            }
            else {
                exit(LEX_ERROR);
            }
            break;

        case 7: //    0.
            if (isdigit(c)) {
                strAddChar(String, c);
                state = 8;
            }
            else exit(LEX_ERROR);
            break;

        case 8: //    0.N
            if (isdigit(c)) {
                strAddChar(String, c);
            }
            else if (c == 'e' || c == 'E') {
                strAddChar(String, c);
                state = 9;
            }
            else if (isspace(c) || c == EOF || c == 10 || c == '+' || c == '-' || c == '/' || c == '*' || '(' || ',' || ')' || ';' || ':') {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_FLOAT;
                ScannerToken.TokenAttribute.FLOAT = atof(String);
                return ScannerToken;
            }
            else exit(LEX_ERROR);
            break;

        case 9:
            if (isdigit(c)) {
                strAddChar(String, c);
                state = 11;
            }
            else if (c == '+' || c == '-') {
                strAddChar(String, c);
                state = 10;
            }
            else {
                exit(LEX_ERROR);
            }
            break;

        case 10:
            if (isdigit(c) && c != '0') {
                strAddChar(String, c);
                state = 11;
            }
            else exit(LEX_ERROR);
            break;

        case 11:
            if (isdigit(c)) {
                strAddChar(String, c);
            }
            else if (isspace(c) || c == EOF || c == 10 || c == '+' || c == '-' || c == '/' || c == '*' || '(' || ',' || ')' || ';' || ':') {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_FLOAT;
                ScannerToken.TokenAttribute.FLOAT = atof(String);
                return ScannerToken;
            }
            else exit(LEX_ERROR);

            break;
        case 12: //INTEGER
            if (isdigit(c)) {
                strAddChar(String, c);
            }
            else if (c == '.') { // INTEGER -> FLOAT
                strAddChar(String, c);
                state = 7;
            }
            else if (isspace(c) || c == EOF || c == 10 || c == '+' || c == '-' || c == '/' || c == '*' || '(' || ',' || ')' || ';' || ':') {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_INT;
                ScannerToken.TokenAttribute.INT = atoi(String);
                return ScannerToken;
            }
            else if (c == 'e' || c == 'E') {
                strAddChar(String, c);
                state = 9;
            }
            else exit(LEX_ERROR);
            break;

        case 13:
            if (c == '=') {
                state = 0;
                strAddChar(String, c);
                ScannerToken.TokenType = Token_Define_Type;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            else {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_Colon;
                ScannerToken.TokenAttribute.STRING = String;
                return ScannerToken;
            }
            break;

        case 14:      // "
            if (c == '\\') {
                state = 16;
            }
            if (c >= 31 && c != 34) {
                strAddChar(String, c);
            }
            else if (c == 34) {
                if (isalnum(CheckNextChar())) {
                    exit(LEX_ERROR);
                }
                else if (CheckNextChar() == 34) {
                    state == 17;
                }
                else {
                    state = 0;
                    ScannerToken.TokenType = Token_STRING;
                    ScannerToken.TokenAttribute.STRING = String;
                    return ScannerToken;
                }
            }
            else exit(LEX_ERROR);
            break;

        case 15:
            if (c == 10) {
                state = 15;
            }
            else {
                ungetc(c, Source);
                state = 0;
                ScannerToken.TokenType = Token_EOL;
                return ScannerToken;
            }
            break;

        case 16:
            if (c == '\"' || 'n' || 't' || '\\') {
                strAddChar(String, c);
                state = 14;
            }
            else exit(LEX_ERROR);
            break;
        case 17:      //""
            if (c == 34) {
                state = 18;
            }
            else if (c != EOF || 10 || '+' || '-' || '/' || '*' || '(' || ',' || ')' || ';' || ':') {
                exit(LEX_ERROR);
            }
            break;
        case 18: //  //
            if (c >= 31) {
                state = 18;
            }
            else if (c == 10) {
                state = 0;
            }
            else if (c == EOF) {
                state = 0;
                ScannerToken.TokenType = Token_EOF;
                return ScannerToken;
            }
            else exit(LEX_ERROR);
            break;
        case 19: // /*
            if ((c >= 31 && c != 42) || c == 10) {
                state = 19;
            }
            else if (c == 42) { // *
                state = 20;
            }
            else exit(LEX_ERROR);
            break;
        case 20: //    /*  *
            if ((c >= 31 && c != '/') || c == 10) {
                state = 19;
            }
            else if (c == '/') {
                if (CheckNextChar() == 10) {
                    state = 21;
                }
                else {
                    state = 0;
                }
            }
            else exit(LEX_ERROR);
            break;
        case 21:
            state = 0;
            ScannerToken.TokenType = Token_EOL;
            return ScannerToken;
            break;
        }
    }
}
char CheckNextChar() {
    char Pom;
    c = getc(Source);
    if (c == 10) {
        Pom = c;
        ungetc(c, Source);
        return Pom;
    }
    while (isspace(c)) {
        c = getc(Source);
        if (c == 10) {
            Pom = c;
            ungetc(c, Source);
            return Pom;
        }
    }
    Pom = c;
    ungetc(c, Source);
    return Pom;
}