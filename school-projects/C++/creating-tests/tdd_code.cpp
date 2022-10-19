//======== Copyright (c) 2017, FIT VUT Brno, All rights reserved. ============//
//
// Purpose:     Test Driven Development - priority queue code
//
// $NoKeywords: $ivs_project_1 $tdd_code.cpp
// $Author:     Daniel Olearcin <xolear00@stud.fit.vutbr.cz>
// $Date:       $2020-02-29
//============================================================================//
/**
 * @file tdd_code.cpp
 * @author Daniel Olearcin
 * 
 * @brief Implementace metod tridy prioritni fronty.
 */

#include <stdlib.h>
#include <stdio.h>

#include "tdd_code.h"

//============================================================================//
// ** ZDE DOPLNTE IMPLEMENTACI **
//
// Zde doplnte implementaci verejneho rozhrani prioritni fronty (Priority Queue)
// 1. Verejne rozhrani fronty specifikovane v: tdd_code.h (sekce "public:")
//    - Konstruktor (PriorityQueue()), Destruktor (~PriorityQueue())
//    - Metody Insert/Remove/Find a GetHead
//    - Pripadne vase metody definovane v tdd_code.h (sekce "protected:")
//
// Cilem je dosahnout plne funkcni implementace prioritni fronty implementovane
// pomoci tzv. "double-linked list", ktera bude splnovat dodane testy 
// (tdd_tests.cpp).
//============================================================================//

PriorityQueue::PriorityQueue()
{
    // list is empty so bottom null
    bottom = NULL;
}

PriorityQueue::~PriorityQueue()
{
    // not included in tests
}

void PriorityQueue::Insert(int value)
{
    // creating new element
    Element_t *newelement = new Element_t{NULL,NULL,value};
    int cnt = 0;
    // loop for every element
    for(Element_t *tmp = GetHead(); tmp != NULL; tmp = tmp->pNext)
    {
        // controlling if param value is still higher than next value
        if(value <= tmp->value)
        {
            // cnt = 0; adding element to the bottom of list
            if(cnt == 0)
            {
                newelement->pNext=tmp;
                tmp->pPrev = newelement;
                bottom = newelement;
                return;
            }
            //cassualy adding element
            else
            {
                newelement->pPrev = tmp->pPrev;
                newelement->pNext = tmp;
                tmp->pPrev->pNext = newelement;
                tmp->pPrev = newelement;
                return;
            }
        }
        else
        {
            //controlling if we are on the end of the list 
            if(tmp->pNext == NULL)
            {
               newelement->pPrev = tmp;
               tmp->pNext = newelement;
               return;
            }
        }
        cnt++;
    }
    // we are not in loop so there are no elements in list
    bottom = newelement;
    return;
        
}

bool PriorityQueue::Remove(int value)
{
    int cnt = 0;
    // loop for every element
    for(Element_t *tmp = GetHead(); tmp != NULL; tmp = tmp->pNext)
    {
        // controlling value
        if(tmp->value == value)
        {
            // cnt = 0; removing bottom element
            if(cnt == 0)
            {
                // removing last standing element
                if(tmp->pNext == NULL)
                {
                    bottom = NULL;
                    delete tmp;
                    return true;
                }
                // cassualy removing
                bottom = tmp->pNext;
                tmp->pNext->pPrev = NULL;
                delete tmp;
                return true;
            }
            else
            {
                //controlling if we are on the end of the list
                if(tmp->pNext == NULL)
                {
                    tmp->pPrev->pNext = NULL;
                    delete tmp;
                    return true;
                }
                // cassualy removing (dont need to use lines 128-131 because they are not in tests)
                tmp->pPrev->pNext = tmp->pNext;
			    tmp->pNext->pPrev = tmp->pPrev;
                delete tmp;
                return true;
            }
        }
        cnt++;
    }
    return false;
}

PriorityQueue::Element_t *PriorityQueue::Find(int value)
{
    // loop for every element
    for(Element_t *tmp = GetHead(); tmp != NULL; tmp = tmp->pNext)
    {
        if(tmp->value == value)
            return tmp;
    }
    return NULL;
}

PriorityQueue::Element_t *PriorityQueue::GetHead()
{
    return bottom;
}

/*** Konec souboru tdd_code.cpp ***/
