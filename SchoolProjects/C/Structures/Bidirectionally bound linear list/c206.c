
/* c206.c **********************************************************}
{* Téma: Dvousměrně vázaný lineární seznam
**
**                   Návrh a referenční implementace: Bohuslav Křena, říjen 2001
**                            Přepracované do jazyka C: Martin Tuček, říjen 2004
**                                            Úpravy: Kamil Jeřábek, září 2020
**
** Implementujte abstraktní datový typ dvousměrně vázaný lineární seznam.
** Užitečným obsahem prvku seznamu je hodnota typu int.
** Seznam bude jako datová abstrakce reprezentován proměnnou
** typu tDLList (DL znamená Double-Linked a slouží pro odlišení
** jmen konstant, typů a funkcí od jmen u jednosměrně vázaného lineárního
** seznamu). Definici konstant a typů naleznete v hlavičkovém souboru c206.h.
**
** Vaším úkolem je implementovat následující operace, které spolu
** s výše uvedenou datovou částí abstrakce tvoří abstraktní datový typ
** obousměrně vázaný lineární seznam:
**
**      DLInitList ...... inicializace seznamu před prvním použitím,
**      DLDisposeList ... zrušení všech prvků seznamu,
**      DLInsertFirst ... vložení prvku na začátek seznamu,
**      DLInsertLast .... vložení prvku na konec seznamu,
**      DLFirst ......... nastavení aktivity na první prvek,
**      DLLast .......... nastavení aktivity na poslední prvek,
**      DLCopyFirst ..... vrací hodnotu prvního prvku,
**      DLCopyLast ...... vrací hodnotu posledního prvku,
**      DLDeleteFirst ... zruší první prvek seznamu,
**      DLDeleteLast .... zruší poslední prvek seznamu,
**      DLPostDelete .... ruší prvek za aktivním prvkem,
**      DLPreDelete ..... ruší prvek před aktivním prvkem,
**      DLPostInsert .... vloží nový prvek za aktivní prvek seznamu,
**      DLPreInsert ..... vloží nový prvek před aktivní prvek seznamu,
**      DLCopy .......... vrací hodnotu aktivního prvku,
**      DLActualize ..... přepíše obsah aktivního prvku novou hodnotou,
**      DLPred .......... posune aktivitu na předchozí prvek seznamu,
**      DLSucc .......... posune aktivitu na další prvek seznamu,
**      DLActive ........ zjišťuje aktivitu seznamu.
**
** Při implementaci jednotlivých funkcí nevolejte žádnou z funkcí
** implementovaných v rámci tohoto příkladu, není-li u funkce
** explicitně uvedeno něco jiného.
**
** Nemusíte ošetřovat situaci, kdy místo legálního ukazatele na seznam 
** předá někdo jako parametr hodnotu NULL.
**
** Svou implementaci vhodně komentujte!
**
** Terminologická poznámka: Jazyk C nepoužívá pojem procedura.
** Proto zde používáme pojem funkce i pro operace, které by byly
** v algoritmickém jazyce Pascalovského typu implemenovány jako
** procedury (v jazyce C procedurám odpovídají funkce vracející typ void).
**/

#include "c206.h"

int solved;
int errflg;

void DLError() {
/*
** Vytiskne upozornění na to, že došlo k chybě.
** Tato funkce bude volána z některých dále implementovaných operací.
**/	
    printf ("*ERROR* The program has performed an illegal operation.\n");
    errflg = TRUE;             /* globální proměnná -- příznak ošetření chyby */
    return;
}

void DLInitList (tDLList *L) {
/*
** Provede inicializaci seznamu L před jeho prvním použitím (tzn. žádná
** z následujících funkcí nebude volána nad neinicializovaným seznamem).
** Tato inicializace se nikdy nebude provádět nad již inicializovaným
** seznamem, a proto tuto možnost neošetřujte. Vždy předpokládejte,
** že neinicializované proměnné mají nedefinovanou hodnotu.
**/

    L->Act = NULL;
    L->First = NULL;
    L->Last = NULL;
    
    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLDisposeList (tDLList *L) {
/*
** Zruší všechny prvky seznamu L a uvede seznam do stavu, v jakém
** se nacházel po inicializaci. Rušené prvky seznamu budou korektně
** uvolněny voláním operace free. 
**/

    while (L->First != NULL){
        L->Act = L->First;
        L->First = L->First->rptr;
        free(L->Act);
    }

    L->Act = NULL;
    L->Last = NULL;
	
    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLInsertFirst (tDLList *L, int val) {
/*
** Vloží nový prvek na začátek seznamu L.
** V případě, že není dostatek paměti pro nový prvek při operaci malloc,
** volá funkci DLError().
**/

    tDLElemPtr new_insert = malloc(sizeof(struct tDLElem));
    if (new_insert == NULL){
        DLError();
        return;
    }
    new_insert->data = val;
    new_insert->lptr = NULL;
    new_insert->rptr = L->First;
    if (L->First != NULL) L->First->lptr = new_insert;
    if (L->Last == NULL) L->Last = new_insert;
    L->First = new_insert;
    
    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLInsertLast(tDLList *L, int val) {
/*
** Vloží nový prvek na konec seznamu L (symetrická operace k DLInsertFirst).
** V případě, že není dostatek paměti pro nový prvek při operaci malloc,
** volá funkci DLError().
**/ 	

    tDLElemPtr new_insert = malloc(sizeof(struct tDLElem));
    if (new_insert == NULL){
        DLError();
        return;
    }
    new_insert->data = val;
    new_insert->rptr = NULL;
    new_insert->lptr = L->Last;
    if (L->First == NULL) L->First = new_insert;
    if (L->Last != NULL) L->Last->rptr = new_insert;
    L->Last = new_insert;
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLFirst (tDLList *L) {
/*
** Nastaví aktivitu na první prvek seznamu L.
** Funkci implementujte jako jediný příkaz (nepočítáme-li return),
** aniž byste testovali, zda je seznam L prázdný.
**/
	
    L->Act = L->First;

    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLLast (tDLList *L) {
/*
** Nastaví aktivitu na poslední prvek seznamu L.
** Funkci implementujte jako jediný příkaz (nepočítáme-li return),
** aniž byste testovali, zda je seznam L prázdný.
**/

	L->Act = L->Last;

	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLCopyFirst (tDLList *L, int *val) {
/*
** Prostřednictvím parametru val vrátí hodnotu prvního prvku seznamu L.
** Pokud je seznam L prázdný, volá funkci DLError().
**/

	if (L->First == NULL){
        DLError();
        return;
    }
    *val = L->First->data;
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLCopyLast (tDLList *L, int *val) {
/*
** Prostřednictvím parametru val vrátí hodnotu posledního prvku seznamu L.
** Pokud je seznam L prázdný, volá funkci DLError().
**/

    if (L->First == NULL){
        DLError();
        return;
    }
    *val = L->Last->data;
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLDeleteFirst (tDLList *L) {
/*
** Zruší první prvek seznamu L. Pokud byl první prvek aktivní, aktivita 
** se ztrácí. Pokud byl seznam L prázdný, nic se neděje.
**/

    if (L->First != NULL){
        if (L->Act == L->First) L->Act = NULL;
        if (L->Last == L->First) L->Last = NULL;
        tDLElemPtr tmp;
        tmp = L->First;
        L->First = L->First->rptr;
        if (L->First != NULL) L->First->lptr = NULL; // ak bol v zozname iba jeden prvok tak by sme mohli pristupovat mimo pamet
        free(tmp);
    }
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}	

void DLDeleteLast (tDLList *L) {
/*
** Zruší poslední prvek seznamu L.
** Pokud byl poslední prvek aktivní, aktivita seznamu se ztrácí.
** Pokud byl seznam L prázdný, nic se neděje.
**/ 

    if (L->First != NULL){
        if (L->Act == L->Last) L->Act = NULL;
        if (L->Last == L->First) L->First = NULL;
        tDLElemPtr tmp;
        tmp = L->Last;
        L->Last = L->Last->lptr;
        if (L->Last != NULL) L->Last->rptr = NULL; // ak bol v zozname iba jeden prvok tak by sme mohli pristupovat mimo pamet
        free(tmp);
    }
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLPostDelete (tDLList *L) {
/*
** Zruší prvek seznamu L za aktivním prvkem.
** Pokud je seznam L neaktivní nebo pokud je aktivní prvek
** posledním prvkem seznamu, nic se neděje.
**/

    if (L->Act != NULL && L->Act != L->Last){
        tDLElemPtr tmp;
        tmp = L->Act->rptr;
        if (L->Act->rptr->rptr == NULL){
            L->Last = L->Act;
            L->Act->rptr = NULL;
        } 
        else{
            L->Act->rptr = L->Act->rptr->rptr;
            L->Act->rptr->lptr = L->Act; 
        }
        free(tmp);
    }

    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLPreDelete (tDLList *L) {
/*
** Zruší prvek před aktivním prvkem seznamu L .
** Pokud je seznam L neaktivní nebo pokud je aktivní prvek
** prvním prvkem seznamu, nic se neděje.
**/

    if (L->Act != NULL && L->Act != L->First){
        tDLElemPtr tmp;
        tmp = L->Act->lptr;
        if (L->Act->lptr->lptr == NULL){
            L->First = L->Act;
            L->Act->lptr = NULL;
        } 
        else {
            L->Act->lptr = L->Act->lptr->lptr;
            L->Act->lptr->rptr = L->Act;
        }
        free(tmp);
    }
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLPostInsert (tDLList *L, int val) {
/*
** Vloží prvek za aktivní prvek seznamu L.
** Pokud nebyl seznam L aktivní, nic se neděje.
** V případě, že není dostatek paměti pro nový prvek při operaci malloc,
** volá funkci DLError().
**/
	if (L->Act != NULL){
        tDLElemPtr new_insert = malloc(sizeof(struct tDLElem));
        if (new_insert == NULL){
            DLError();
            return;
        }
        new_insert->data = val;
        if (L->Act == L->Last){
            new_insert->rptr = NULL;
            L->Last = new_insert;
        }
        else{
            L->Act->rptr->lptr = new_insert;
            new_insert->rptr = L->Act->rptr;
        }
        new_insert->lptr = L->Act;
        L->Act->rptr = new_insert;
    }
	
    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLPreInsert (tDLList *L, int val) {
/*
** Vloží prvek před aktivní prvek seznamu L.
** Pokud nebyl seznam L aktivní, nic se neděje.
** V případě, že není dostatek paměti pro nový prvek při operaci malloc,
** volá funkci DLError().
**/

    if (L->Act != NULL){
        tDLElemPtr new_insert = malloc(sizeof(struct tDLElem));
        if (new_insert == NULL){
            DLError();
            return;
        }
        new_insert->data = val;
        if (L->Act == L->First){
            new_insert->lptr = NULL;
            L->First = new_insert;
        }
        else{
            new_insert->lptr = L->Act->lptr;
            L->Act->lptr->rptr = new_insert;
        }
        new_insert->rptr = L->Act;
        L->Act->lptr = new_insert;
    }
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLCopy (tDLList *L, int *val) {
/*
** Prostřednictvím parametru val vrátí hodnotu aktivního prvku seznamu L.
** Pokud seznam L není aktivní, volá funkci DLError ().
**/
		
    if (L->Act == NULL){
        DLError ();
        return;
    }
    *val = L->Act->data;
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLActualize (tDLList *L, int val) {
/*
** Přepíše obsah aktivního prvku seznamu L.
** Pokud seznam L není aktivní, nedělá nic.
**/

    if (L->Act != NULL){
        L->Act->data = val;
    }
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

void DLSucc (tDLList *L) {
/*
** Posune aktivitu na následující prvek seznamu L.
** Není-li seznam aktivní, nedělá nic.
** Všimněte si, že při aktivitě na posledním prvku se seznam stane neaktivním.
**/

	if (L->Act != NULL){
        L->Act = L->Act->rptr;
    }
	
    //solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}


void DLPred (tDLList *L) {
/*
** Posune aktivitu na předchozí prvek seznamu L.
** Není-li seznam aktivní, nedělá nic.
** Všimněte si, že při aktivitě na prvním prvku se seznam stane neaktivním.
**/

    if (L->Act != NULL){
        L->Act = L->Act->lptr;
    }

	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

int DLActive (tDLList *L) {
/*
** Je-li seznam L aktivní, vrací nenulovou hodnotu, jinak vrací 0.
** Funkci je vhodné implementovat jedním příkazem return.
**/

    return (L->Act != NULL);
	
	//solved = FALSE;                   /* V případě řešení, smažte tento řádek! */
}

/* Konec c206.c*/
