
/**
 * Projekt 1. (Práce s textem)
 * Daniel Olearčin <xolear00@stud.fit.vutbr.cz>
 */


#include <stdio.h>
#include <stdlib.h>
#include <string.h>

#define LINE_LEN 101


char *all_small(char *fullname)
{
    int i = 0;
    while(fullname[i] != '\0')
    {
        if((int)fullname[i] <= 'Z' && (int)fullname[i] >= 'A')
            // due to ASCII
            fullname[i] = (int)fullname[i] + ('a' - 'A');
        i++;
    }
    return fullname;
}



int verification_name(char *CISLO, char *fullname)
{
    // declaration and initializations for loops
    char *chrs;
    int j = 0;
    int finish = 0;

    // quantity of chars in CISLO
    int len_cislo = (int)strlen(CISLO);
    int len_chrs;

    // loop for each char from CISLO
    for(int i = 0; i < len_cislo; i++)
    {
       
       // allocate to chars relative to CISLO[i]
        switch (CISLO[i])
        {
        case '1':
            chrs = "1";
            break;
        case '2':
            chrs = "2abc";
            break;
        case '3':
            chrs = "3def";
            break;
        case '4':
            chrs = "4ghi";
            break;
        case '5':
            chrs = "5jkl";
            break;
        case '6':
            chrs = "6mno";
            break;
        case '7':
            chrs = "7pqrs";
            break;
        case '8':
            chrs = "8tuv";
            break;
        case '9':
            chrs = "9wxyz";
            break;
        case '0':
            chrs = "0+";
            break;
        default:
            return 0;
        }
        finish = 0;

        // quantity of chars in chrs
        len_chrs = (int)strlen(chrs);
        
        // looking if one of chars in chrs matches char in fullname
        while(fullname[j] != '\0')
            {
                for(int k = 0; k < len_chrs; k++)
                {
                    
                    // if found, going out of the loop and moving to the next char from CISLO
                    if(chrs[k] == fullname[j])
                    {
                        finish = 1;
                        break;
                    }
                }
                if(finish == 1)
                    break;
                j++;
            }
            
            // if not found return 0
            if(finish == 0)
                return 0;
    }
    
    // if everything went smoothly return 1
    return 1;
}

int verification_number(char *CISLO, char *number)
{
    
    // initializations for loop
    int i = 0;
    int j = 0;
    int counter = 0;
    
    //quantity of chars in CISLO 
    int len_CISLO = (int)strlen(CISLO);
    
    // looking for each char from CISLO in number
    // if found counter++
    while (number[i] != '\0' && j < len_CISLO)
    {
        if (CISLO[j] == number[i])
        {
            j++;
            i++;
            counter++;
        }
        else
        {
            i++;
        }
        
    }
    // if counter matches len_CISLO return 1
    // if not return 0
    if (counter == len_CISLO)
        return 1;
    else 
        return 0;
}

int main(int argc, char *argv[])
{
    int p = 0;
    char fullname[LINE_LEN + 1];
    char number[LINE_LEN + 1];

    //  checking arguments
    if (argv[1] == NULL)
    {
        while((fgets(fullname, LINE_LEN + 2, stdin)) != NULL)
        {
            fgets(number, LINE_LEN + 2, stdin);
            if(strlen(fullname) > LINE_LEN)
            {
                fputs("Error - too many chars\n", stderr);
                return -1;
            }
            printf("%.*s, %s", (int)strlen(fullname) - 1, fullname, number);
        }
        p = 1;
    }
    
    else
    {

        // checking quantity of arguments
        if(argc > 2)
        {
            fputs("ERROR - too many arguments\n", stderr);
            return -1;
        }
    
        else
        {
            char *CISLO = argv[1];

            // loop for every fullname and number
            while((fgets(fullname, LINE_LEN + 2, stdin)) != NULL)
            {
                if (strlen(fullname) > LINE_LEN)
                {
                    fputs("ERROR - too many chars\n", stderr);
                    return -1;
                }
                fgets(number, LINE_LEN + 2, stdin);

                // transforming big letters to small 
                all_small(fullname);
                
                // check name and number
                if((verification_number(CISLO, number)) == 1 || (verification_name(CISLO, fullname) == 1))
                {
                    printf("%.*s, %s", (int)strlen(fullname) - 1, fullname, number);
                    p = 1;
                }
                
            }
        }
    }
    
    // checking condition
    if(p == 0) 
        printf("Not found");
    
    return 0;
}
