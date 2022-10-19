#include <stdio.h>
#include <stdlib.h>
#include "parser.h"
#include "scanner.h"

int main(int argc, char* argv[])
{
    int error = ALL_OK;
    FILE* pFile;
    if (argc < 2) {
        pFile = stdin;
        if (pFile != NULL)
        {
            setSourceFile(pFile);
            if (!program())
                error = PREC_SYNTAX_ERROR;
            fclose(pFile);
        }
    }
    return error;
}