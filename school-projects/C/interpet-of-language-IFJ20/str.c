#include <string.h>
#include <malloc.h>
#include "str.h"

#define STR_LEN_INC 8
//konstanta pro alokaci mista na jeden znak

enum RETURN{ALL_OK,ALL_ERROR,ADD_OK,COPY_OK};


int strInit(string *s) {
  if ((s->str = (char*) malloc(STR_LEN_INC)) == NULL) {
    return ALL_ERROR;
  }
    s->str[0] = '\0';
    s->length = 0;
    s->allocSize = STR_LEN_INC;
    return ALL_OK;
}

void strFree(string *s) {
  free(s->str);
}

void strClear(string *s)
{
   s->str[0] = '\0';
   s->length = 0;
}

int strAddChar(string *s1, char c)
// prida na konec retezce jeden znak
{
   if (s1->length + 1 >= s1->allocSize)
   {
      // pamet nestaci, je potreba provest realokaci
      if ((s1->str = (char*) realloc(s1->str, s1->length + STR_LEN_INC)) == NULL)
         return ALL_ERROR;
      s1->allocSize = s1->length + STR_LEN_INC;
   }
   s1->str[s1->length] = c;
   s1->length++;
   s1->str[s1->length] = '\0';
   return ADD_OK;
}

int strCopyString(string *s1, string *s2)
// prekopiruje retezec s2 do s1
{
   int newLength = s2->length;
   if (newLength >= s1->allocSize)
   {
      // pamet nestaci, je potreba provest realokaci
      if ((s1->str = (char*) realloc(s1->str, newLength + 1)) == NULL)
         return ALL_ERROR;
      s1->allocSize = newLength + 1;
   }
   strcpy(s1->str, s2->str);
   s1->length = newLength;
   return COPY_OK;
}

int strCmpString(string *s1, string *s2)
// porovna oba retezce a vrati vysledek
{
   return strcmp(s1->str, s2->str);
}

int strCmpConstStr(string *s1, char* s2)
// porovna nas retezec s konstantnim retezcem
{
   return strcmp(s1->str, s2);
}

char *strGetStr(string *s)
// vrati textovou cast retezce
{
   return s->str;
}

int strGetLength(string *s)
// vrati delku daneho retezce
{
   return s->length;
}
