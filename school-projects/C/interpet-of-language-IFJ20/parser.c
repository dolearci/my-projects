#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>

#include "parser.h"
#include "scanner.h"

Token token;

bool valid = true;
bool invalid = false;
int bracket = 0;
bool debug1 = true;
bool debug2 = false;
tHTItem* UNDEFPTR;
tHTable* globalniTS;
tKey lastFuncID;

Item lokalniTS;

tHTable* init_table(tHTable* ptrht) {
	UNDEFPTR = (tHTItem*)malloc(sizeof(tHTable));
	UNDEFPTR->key = "*UNDEF*";
	UNDEFPTR->data.defined = false;
	UNDEFPTR->data.type = TYPE_UNDEFINED;
	UNDEFPTR->ptrnext = NULL;
	ptrht = (tHTable*)malloc(sizeof(tHTable));
	for (int i = 0; i < MAX_HTSIZE; (*ptrht)[i++] = UNDEFPTR);
}

void debug_tables() {
	UNDEFPTR = (tHTItem*)malloc(sizeof(tHTable));
	UNDEFPTR->key = "*UNDEF*";
	UNDEFPTR->data.defined = false;
	UNDEFPTR->data.type = TYPE_UNDEFINED;
	UNDEFPTR->ptrnext = NULL;
	globalniTS = (tHTable*)malloc(sizeof(tHTable));
	for (int i = 0; i < MAX_HTSIZE; (*globalniTS)[i++] = UNDEFPTR);
	/*
	printf("\n[TEST01] Table initialization\n");
	use_init(ptrht);
	//htPrintTable(ptrht);
	printf("\n[TEST02] Let's try htInsert()\n");
	use_insert(ptrht, "krusovice", 21.50);
	htPrintTable(ptrht);
	*/

}

void use_init(tHTable* ptrht) {
	htInit(ptrht);
}

void use_insert(tHTable* ptrht, tKey key, tData data) {
	htInsert(ptrht, key, data);
}

tData generateData(Token token, bool isFunc, bool isDef) {
	tData data;
	if (isFunc) {
		data.funkce = true;
		data.type = TYPE_FUNC;
		data.defined = isDef;
		data.id = strGetStr(token.TokenAttribute.STRING);
		lastFuncID = data.id;
	}
	return data;
}


void htPrintTable(tHTable* ptrht) {
	int maxlen = 0;
	int sumcnt = 0;

	printf("------------HASH TABLE--------------\n");
	for (int i = 0; i < HTSIZE; i++) {
		//printf("%i:", i);
		int cnt = 0;
		tHTItem* ptr = (*ptrht)[i];
		bool enter = false;
		if (strcmp(ptr->key, "*UNDEF*") == 1) {
			printf("%i:", i);
			enter = true;
		}
		while (ptr != NULL) {
			if (strcmp(ptr->key, "*UNDEF*") == 1)
			{
				switch (ptr->data.type) {
				case TYPE_UNDEFINED:
					printf(" (%s,%s)", ptr->key, getDataType(ptr->data.type));
					break;
				case TYPE_FUNC:
					if (ptr->data.defined)
						printf(" (%s,%s) def: %s", ptr->key, getDataType(ptr->data.type), ptr->data.defined ? "ano" : "ne");
					else
						printf(" (%s,%s) def: %s", ptr->key, getDataType(ptr->data.type), ptr->data.defined ? "ano" : "ne");
					break;
				case TYPE_INT:
					printf(" (%s,%s)", ptr->key, getDataType(ptr->data.type));
					break;
				case TYPE_FLOAT:
					printf(" (%s,%s)", ptr->key, getDataType(ptr->data.type));
					break;
				case TYPE_STRING:
					printf(" (%s,%s)", ptr->key, getDataType(ptr->data.type));
					break;
				}
			}
			if (ptr != UNDEFPTR)
				cnt++;
			ptr = ptr->ptrnext;
		}
		if (enter)
			printf("\n");
		if (cnt > maxlen)
			maxlen = cnt;
		sumcnt += cnt;
	}

	printf("------------------------------------\n");
	printf("Items count %i   The longest list %i\n", sumcnt, maxlen);
	printf("------------------------------------\n");
}

char* getDataType(tType type) {
	switch (type) {
	case TYPE_UNDEFINED:
		return "TYPE_UNDEFINED";
	case TYPE_FUNC:
		return "TYPE_FUNC";
	case TYPE_INT:
		return "TYPE_INT";
	case TYPE_FLOAT:
		return "TYPE_FLOAT";
	case TYPE_STRING:
		return "TYPE_STRING";
	}
	return "xx";
}

void Get_token()
{
	token = getNextToken();
	if (debug2)
		printf("%s\n", getTokenString(token));
}
char* getTokenString(Token token) {
	switch (token.TokenType) {
	case Token_EOF:
		return "Token_EOF";
	case Token_EOL:
		return "Token_EOL";
	case Token_Keyword:
		return getKeyword(token);
	case Token_Identifier:
		printf("ID: %s\n", strGetStr(token.TokenAttribute.STRING));
		return "Token_Identifier";
	case Token_Plus:
		return "Token_Plus";
	case Token_Minus:
		return "Token_Minus";
	case Token_Multiply:
		return "Token_Multiply";
	case Token_Divide:
		return "Token_Divide";
	case Token_Left_Bracket:
		return "Token_Left_Bracket";
	case Token_Right_Bracket:
		return "Token_Right_Bracket";
	case Token_Left_Brace:
		return "Token_Left_Brace";
	case Token_Right_Brace:
		return "Token_Right_Brace";
	case Token_Greater:
		return "Token_Greater";
	case Token_Greater_Or_Equal:
		return "Token_Greater_Or_Equal";
	case Token_Lesser:
		return "Token_Lesser";
	case Token_Lesser_Or_Equal:
		return "Token_Lesser_Or_Equal";
	case Token_Equal:
		return "Token_Equal";
	case Token_Assign:
		return "Token_Assign";
	case Token_Define_Type:
		return "Token_Define_Type";
	case Token_Not_Equal:
		return "Token_Not_Equal";
	case Token_INT:
		return "Token_INT";
	case Token_FLOAT:
		return "Token_FLOAT";
	case Token_STRING:
		return "Token_STRING";
	case Token_BOOL:
		return "Token_BOOL";
	case Token_Comma:
		return "Token_Comma";
	case Token_Colon:
		return "Token_Colon";
	case Token_SemiColon:
		return "Token_SemiColon";
	case Token_Dot:
		return "Token_Dot";
	case Token_DokKom:
		return "Token_DokKom";
	default:
		return "x";
	}
}
char* getKeyword(Token token) {
	switch (token.TokenAttribute.KEYWORD) {
	case Key_int:
		return "Key_int";
	case Key_float64:
		return "Key_float64";
	case Key_else:
		return "Key_else";
	case Key_if:
		return "Key_if";
	case Key_for:
		return "Key_for";
	case Key_func:
		return "Key_func";
	case Key_package:
		return "Key_package";
	case Key_return:
		return "Key_return";
	case Key_string:
		return "Key_string";
	case Key_inputs:
		return "Key_inputs";
	case Key_inputi:
		return "Key_inputi";
	case Key_inputf:
		return "Key_inputf";
	case Key_len:
		return "Key_len";
	case Key_print:
		return "Key_print";
	case Key_int2float:
		return "Key_int2float";
	case Key_float2int:
		return "Key_float2int";
	case Key_substr:
		return "Key_substr";
	case Key_ord:
		return "Key_ord";
	case Key_chr:
		return "Key_chr";
	default:
		return "kw_x";
	}
}
bool term() {
	if (debug1) {
		printf("TERM\n");
		printf("%s \n", invalid ? "true" : "false");
	}
	if (token.TokenType == Token_Plus || token.TokenType == Token_Minus || token.TokenType == Token_Multiply || token.TokenType == Token_Divide || token.TokenType == Token_Greater || token.TokenType == Token_Greater_Or_Equal || token.TokenType == Token_Lesser || token.TokenType == Token_Lesser_Or_Equal || token.TokenType == Token_Equal || token.TokenType == Token_Not_Equal || token.TokenType == Token_Left_Bracket || token.TokenType == Token_Right_Bracket || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT) {
		if (token.TokenType == Token_Plus || token.TokenType == Token_Minus) {
			Get_token();
			if (token.TokenType == Token_Left_Bracket || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT || token.TokenType == Token_STRING) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
		else if (token.TokenType == Token_Multiply || token.TokenType == Token_Divide) {
			Get_token();
			if (token.TokenType == Token_Left_Bracket || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
		else if (token.TokenType == Token_Greater || token.TokenType == Token_Greater_Or_Equal || token.TokenType == Token_Lesser || token.TokenType == Token_Lesser_Or_Equal || token.TokenType == Token_Equal || token.TokenType == Token_Not_Equal) {
			Get_token();
			if (token.TokenType == Token_Plus || token.TokenType == Token_Minus || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT || token.TokenType == Token_Left_Bracket || token.TokenType == Token_STRING) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
		else if (token.TokenType == Token_Left_Bracket) {
			bracket = bracket + 1;
			Get_token();
			if (token.TokenType == Token_Left_Bracket || token.TokenType == Token_Right_Bracket || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
		else if (token.TokenType == Token_Right_Bracket) {
			bracket = bracket - 1;
			if (bracket < 0) {
				if (valid == true && invalid == false) {
					bracket = 0;
					return true;
				}
				else {
					bracket = 0;
					return false;
				}
			}
			Get_token();
			if (token.TokenType == Token_Comma || token.TokenType == Token_Left_Bracket || token.TokenType == Token_Right_Bracket || token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT || token.TokenType == Token_EOL || token.TokenType == Token_Plus || token.TokenType == Token_Minus || token.TokenType == Token_Multiply || token.TokenType == Token_Divide || token.TokenType == Token_Greater || token.TokenType == Token_Greater_Or_Equal || token.TokenType == Token_Lesser || token.TokenType == Token_Lesser_Or_Equal || token.TokenType == Token_Equal || token.TokenType == Token_Not_Equal || token.TokenType == Token_SemiColon || token.TokenType == Token_Left_Brace) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
		else if (token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_FLOAT) {
			Get_token();
			if (token.TokenType == Token_Comma || token.TokenType == Token_Left_Bracket || token.TokenType == Token_Plus || token.TokenType == Token_Minus || token.TokenType == Token_Multiply || token.TokenType == Token_Divide || token.TokenType == Token_Right_Bracket || token.TokenType == Token_Greater || token.TokenType == Token_Greater_Or_Equal || token.TokenType == Token_Lesser || token.TokenType == Token_Lesser_Or_Equal || token.TokenType == Token_Equal || token.TokenType == Token_Not_Equal || token.TokenType == Token_EOL || token.TokenType == Token_SemiColon || token.TokenType == Token_Left_Brace || token.TokenType == Token_Assign) {
				valid = true;
				term();
			}
			else {
				invalid = true;
				term();
			}
		}
	}
	else if (token.TokenType == Token_EOL) {
		if (bracket != 0 || valid == false || invalid == true) {
			bracket = 0;
			return false;
		}
		else {
			bracket = 0;
			return true;
		}
	}
	else if (valid == true && invalid == false) {
		bracket = 0;
		return true;
	}
	else {
		bracket = 0;
		return false;
	}
}
bool control_keywords()
{
	if (token.TokenType == Token_Identifier || (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_for || token.TokenAttribute.KEYWORD == Key_if ||
		token.TokenAttribute.KEYWORD == Key_return || token.TokenAttribute.KEYWORD == Key_print)))
	{
		return true;
	}
	return false;
}
bool program()
{
	debug_tables();
	if (debug2)
		htPrintTable(globalniTS);
	if (debug1)
		printf("======\n");
	Get_token();
	while (token.TokenType == Token_EOL)
	{
		Get_token();
	}
	if (packages())
	{
		if (token.TokenType == Token_EOF)
		{
			//print cele globalni tabulky
			if (debug2)
				htPrintTable(globalniTS);
			return true;
		}
		else if (def_func())
		{
			if (token.TokenType == Token_EOF)
			{
				//print cele globalni tabulky
				if(debug2)
					htPrintTable(globalniTS);
				return true;
			}
		}
	}
	//print cele globalni tabulky
	if (debug2)
		htPrintTable(globalniTS);
	return false; //error
}
bool packages()
{
	if (debug1)
		printf("FUNCTION: PACKAGES\n");
	if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_package)
	{
		Get_token();
		if (token.TokenType == Token_Identifier)
		{
			//insert id fce do tabulky funkci (globalni)
			tData data_f = generateData(token, true, false);
			use_insert(globalniTS, strGetStr(token.TokenAttribute.STRING), data_f);
			Get_token();
			while (token.TokenType == Token_EOL)
			{
				Get_token();
			}
			if (packages_n())
				{
					return true;
				}
		}
	}
	return false; //error
}
bool packages_n()
{
	if (debug1)
		printf("PACKAGESN\n");
	if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_package)
	{
		if (packages())
		{
			if (packages_n())
			{
				return true;
			}
		}
	}
	else if ((token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_func) || token.TokenType == Token_EOF)
	{
		return true;
	}
	return false;
}
bool def_func()
{
	if (debug1)
		printf("DEFFUNC\n");
	if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_func)
	{
		if (func_declaration());
		{
			Get_token();
			if (def_func())
			{
				return true;
			}
		}
	}
	else if (token.TokenType == Token_EOF)
	{
		return true;
	}
	return false; //error
}
bool func_declaration()
{
	if (debug1)
		printf("FUNCDEC\n");
	if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_func)
	{
		Get_token();
		if (token.TokenType == Token_Identifier)
		{
			//insert id fce do tabulky funkci (globalni)
			tData data_f = generateData(token, true, true);
			use_insert(globalniTS, strGetStr(token.TokenAttribute.STRING), data_f);
			Get_token();
			if (token.TokenType == Token_Left_Bracket)
			{
				if (parameters())
				{
					Get_token();
					if (token.TokenType == Token_Left_Bracket)
					{
						if (types())
						{
							Get_token();
							if (token.TokenType == Token_Left_Brace)
							{
								if (body())
								{
									Get_token();
									return true;
								}
							}
						}
					}
					else if (token.TokenType == Token_Left_Brace)
					{
						if (body())
						{
							Get_token();
							return true;
						}
					}
				}
			}
		}
	}
	return false; //error
}
bool parameters()
{
	if (debug1)
		printf("FUNCTION: PARAMETERS\n");
	Get_token();
	if (token.TokenType == Token_Right_Bracket)
	{
		return true;
	}
	else if (token.TokenType == Token_Identifier)
	{
		//nastavit jako parametr funkce
		//nova lokalni tabulka funkce
		Get_token();
		if (type())
		{
			if (parameters_n())
			{
				return true;
			}
		}
	}
	return false; //error
}
bool parameters_n()
{
	if (debug1)
		printf("FUNCTION: PARAMETERSN\n");
	Get_token();
	if (token.TokenType == Token_Right_Bracket)
	{
		return true;
	}
	else if (token.TokenType == Token_Comma)
	{
		Get_token();
		if (token.TokenType == Token_Identifier)
		{
			Get_token();
			if (type())
			{
				if (parameters_n())
				{
					return true;
				}
			}
		}
	}
	return false;
}
bool types()
{
	if (debug1)
		printf("FUNCTION: TYPES\n");
	Get_token();
	if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_int || token.TokenAttribute.KEYWORD == Key_float64 || token.TokenAttribute.KEYWORD == Key_string))
	{
		if (type())
		{
			if (type_n())
			{
				return true;
			}
		}
	}
	return false; //error
}
bool type()
{
	if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_int || token.TokenAttribute.KEYWORD == Key_float64 || token.TokenAttribute.KEYWORD == Key_string))
	{
		return true;
	}
	return false; //error
}
bool type_n()
{
	Get_token();
	if (token.TokenType == Token_Right_Bracket)
	{
		return true;
	}
	else if (token.TokenType == Token_Comma)
	{
		Get_token();
		if (type())
		{
			if (type_n())
			{
				return true;
			}
		}
	}
	return false; //error
}
bool body()
{
	Get_token();
	if (token.TokenType == Token_EOL)
	{
		Get_token();
		if (control_keywords())
		{
			if (commands())
			{
				return true;
			}
		}
	}
	return false; //error
}
bool commands()
{
	if (debug1)
		printf("FUNCTION: COMMANDS\n");
	if (control_keywords())
	{
		if (command())
		{
			if (token.TokenType == Token_EOL)
			{
				while (token.TokenType == Token_EOL)
				{
					Get_token();
				}
				if (commands())
				{
					return true;
				}
			}
			if (token.TokenType == Token_EOF)
			{
				return true;
			}
			if (token.TokenType == Token_Right_Brace)
			{
				Get_token();
				return true;
			}
		}
	}
	else if (token.TokenType == Token_Right_Brace)
	{
		return true;
	}
	return false; //error
}
bool command()
{
	if (debug1)
		printf("FUNCTION: COMMAND\n");
	if (token.TokenType == Token_Identifier)
	{
		//nastrel
		/*
		lokalniTS.prev = NULL;
		lokalniTS.ptrGlobalTS = htSearch(globalniTS, lastFuncID);
		lokalniTS.localTS = init_table(lokalniTS.localTS);
		*/
		//tData data_f = generateData(token, true, false);
		//use_insert(lokalniTS.localTS, strGetStr(token.TokenAttribute.STRING), data_f);
		if (command_id())
		{
			return true;
		}
	}
	else if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_if)
	{
		Get_token();
		if (term())
		{
			if (token.TokenType == Token_Left_Brace)
			{
				Get_token();
				{
					if (token.TokenType == Token_EOL)
					{
						Get_token();
						if (commands())
						{
							if (token.TokenType == Token_Right_Brace)
							{
								Get_token();
								if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_else)
								{
									Get_token();
									if (token.TokenType == Token_Left_Brace)
									{
										Get_token();
										if (token.TokenType == Token_EOL)
										{
											Get_token();
											if (commands())
											{
												if (token.TokenType == Token_Right_Brace)
												{
													Get_token();
													return true;
												}
											}
										}
									}
								}
								else
								{
									return true;
								}
							}
						}
					}
				}
			}
		}
	}
	else if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_for)
	{
		if (definition())
		{
			if (token.TokenType == Token_SemiColon)
			{
				Get_token();
				if (term()) //term musi nacitavat koniec
				{
					if (token.TokenType == Token_STRING)
					{
						Get_token();
					}
					if (token.TokenType == Token_SemiColon)
					{
						if (assign())
						{
							if (token.TokenType == Token_Left_Brace)
							{
								Get_token();
								if (token.TokenType == Token_EOL)
								{
									Get_token();
									if (commands())
									{
										if (token.TokenType == Token_Right_Brace)
										{
											Get_token();
											return true;
										}
									}
								}
							}
						}
					}
				}
			}
		}
	}
	else if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_return)
	{
		Get_token();
		if (id_n())
		{
			return true;
		}
	}
	else if (token.TokenType == Token_Keyword && token.TokenAttribute.KEYWORD == Key_print)
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (id_n())
			{
				if (token.TokenType == Token_Right_Bracket)
				{
					Get_token();
					return true;
				}
			}
		}
	}
	return false; //error
}
bool command_id()
{
	if (debug1)
		printf("COMMANDID\n");
	Get_token();
	if (token.TokenType == Token_Left_Bracket)
	{
		Get_token();
		if (token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_STRING || token.TokenType == Token_FLOAT)
		{
			if (id_n())
			{
				if (token.TokenType == Token_Right_Bracket)
				{
					Get_token();
					return true;
				}
			}
		}
		else if (token.TokenType == Token_Right_Bracket)
		{
			Get_token();
			return true;
		}
	}
	else if (token.TokenType == Token_Comma)
	{
		if (id_n())
		{
			if (token.TokenType == Token_Assign)
			{
				if (assign_right())
				{
					return true;
				}
			}
		}
	}
	else if (token.TokenType == Token_Define_Type)
	{
		Get_token();
		if (token.TokenType == Token_STRING)
		{
			Get_token();
			return true;
		}
		else if (term())
		{
			return true;
		}
	}
	else if (token.TokenType == Token_Assign)
	{
		if (assign_right())
		{
			return true;
		}
	}
	return false; //error
}
bool definition()
{
	if (debug1)
		printf("FUNCTION: DEFINITION\n");
	Get_token();
	if (token.TokenType == Token_Identifier)
	{
		Get_token();
		if (token.TokenType == Token_Define_Type)
		{
			Get_token();
			if (term())
			{
				return true;
			}
		}
	}
	else if (token.TokenType == Token_SemiColon)
	{
		return true;
	}
	return false;
}
bool assign()
{
	if (debug1)
		printf("FUNCTION: ASSIGN\n");
	Get_token();
	if (token.TokenType == Token_Identifier)
	{
		if (id_n())
		{
			if (token.TokenType == Token_Assign)
			{
				if (assign_right())
				{
					return true;
				}
			}
		}
	}
	else if (token.TokenType == Token_Left_Brace)
	{
		return true;
	}
	return false;
}
bool assign_right()
{
	if (debug1)
		printf("FUNCTION: ASSIGN_RIGHT\n");
	Get_token();
	if (token.TokenType == Token_Identifier)
	{
		if (id_n())
		{
			if (token.TokenType == Token_Left_Bracket)
			{
				Get_token();
				if (id_n())
				{
					if (token.TokenType == Token_Right_Bracket)
					{
						Get_token();
						return true;
					}
				}
			}
			else if (token.TokenType == Token_EOL)
			{
				return true;
			}
			else if (token.TokenType == Token_Left_Brace)
			{
				return true;
			}
		}
	}
	else if (token.TokenType == Token_STRING)
	{
		Get_token();
		return true;
	}
	else if (token.TokenType == Token_Left_Bracket || token.TokenType == Token_FLOAT || token.TokenType == Token_INT)
	{
		if (id_n())
		{
			return true;
		}
	}
	else if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_inputf || token.TokenAttribute.KEYWORD == Key_inputs || token.TokenAttribute.KEYWORD == Key_inputi))
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (token.TokenType == Token_Right_Bracket)
			{
				Get_token();
				return true;
			}
		}
	}
	else if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_float2int || token.TokenAttribute.KEYWORD == Key_int2float || token.TokenAttribute.KEYWORD == Key_chr))
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (term())
			{
				if (token.TokenType == Token_Right_Bracket)
				{
					return true;
				}
			}
		}
	}
	else if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_len))
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (token.TokenType == Token_STRING || token.TokenType == Token_Identifier)
			{
				Get_token();
				if (token.TokenType == Token_Right_Bracket)
				{
					Get_token();
					return true;
				}
			}
		}
	}
	else if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_ord))
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (token.TokenType == Token_STRING || token.TokenType == Token_Identifier)
			{
				Get_token();
				if (token.TokenType == Token_Comma)
				{
					Get_token();
					if (term())
					{
						if (token.TokenType == Token_Right_Bracket)
						{
							Get_token();
							return true;
						}
					}
				}
			}
		}
	}
	else if (token.TokenType == Token_Keyword && (token.TokenAttribute.KEYWORD == Key_substr))
	{
		Get_token();
		if (token.TokenType == Token_Left_Bracket)
		{
			Get_token();
			if (token.TokenType == Token_STRING || token.TokenType == Token_Identifier)
			{
				Get_token();
				if (token.TokenType == Token_Comma)
				{
					Get_token();
					if (term())
					{
						if (token.TokenType == Token_Comma)
						{
							Get_token();
							if (term())
							{
								if (token.TokenType == Token_Right_Bracket)
								{
									Get_token();
									return true;
								}
							}
						}
					}
				}
			}
		}
	}
	return false;
}
bool id_n()
{
	if (debug1)
		printf("ID_N\n");
	if (token.TokenType == Token_STRING)
	{
		Get_token();
	}
	else if (token.TokenType == Token_INT || token.TokenType == Token_FLOAT || token.TokenType == Token_Left_Bracket || token.TokenType == Token_Identifier)
	{
		if (!term())
		{
			return false;
		}
	}
	if (token.TokenType == Token_Right_Bracket || token.TokenType == Token_Left_Brace || token.TokenType == Token_Assign || token.TokenType == Token_EOL || token.TokenType == Token_Left_Bracket)
	{
		return true;
	}
	else if (token.TokenType == Token_Comma)
	{
		Get_token();
		if (token.TokenType == Token_Identifier || token.TokenType == Token_INT || token.TokenType == Token_STRING || token.TokenType == Token_FLOAT || token.TokenType == Token_Left_Bracket)
		{
			if (id_n())
			{
				return true;
			}
		}
	}
	return false;
}
