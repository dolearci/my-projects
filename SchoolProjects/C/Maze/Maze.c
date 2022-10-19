/**
 * Projekt 3. (Práce s datovými strukturami)
 * Daniel Olearčin <xolear00@stud.fit.vutbr.cz>
 */

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <string.h>

#define DOWN 1
#define UP 2
#define LEFT 1
#define RIGHT 2
#define UP_DOWN 4


// converting char to int
double  char_to_int(char *str)
{
    char *ptr = NULL;
    int nbr = strtol(str, &ptr, 10);
    if(*ptr != '\0' || str[0] == '\0')
        return -1;
    return nbr;
}

typedef struct {
    int rows;
    int cols;
    int *cells;
} Map;

// checking if triangle is up or down
int up_down_triangle(int r, int c)
{
    if(((r+c) % 2) == 0)
        return DOWN;
    return UP;
}

// checking if border is there
bool isborder(Map *map, int r, int c, int border)
{
    return ((map->cells[(r-1)*map->cols + c - 1]) & border);
}

// first move 
void first_move(int *rows_st, int *cols_st, int start, int leftright, Map *Map)
{
    if(leftright == 1)
        {
            int up_down = up_down_triangle(*rows_st,*cols_st);
            if(up_down == DOWN)
            {
                if(isborder(Map,*rows_st,*cols_st,start))
                {
                    if(start == LEFT)
                        *cols_st += 1;
                    else if (start == RIGHT)
                        *rows_st -= 1;
                    else 
                        *cols_st -= 1;
                }
                else
                {
                    if(start == LEFT)
                        *cols_st -= 1;
                    else if(start == RIGHT)
                        *cols_st += 1;
                    else
                        *rows_st -= 1;
                }
            }
            else
            {
                if(isborder(Map,*rows_st,*cols_st,start))
                {
                    if(start == LEFT)
                        *rows_st += 1;
                    else if (start == RIGHT)
                        *cols_st -= 1;
                    else 
                        *cols_st += 1;
                }
                else
                {
                    if(start == LEFT)
                        *cols_st -= 1;
                    else if(start == RIGHT)
                        *cols_st += 1;
                    else
                        *rows_st += 1;
                }
            }
        }
        return;
}

// testing file
int test_func(char *filepath)
{
    FILE * file;
    file = fopen(filepath,"r");
    if(file == NULL)
    {
        fputs("Opening file error\n", stderr);
        return -1;
    }
    int rows,cols;
    fscanf(file,"%d %d",&rows, &cols);
    if(rows <= 0 || cols <= 0)
        return 0;
    int i = 0;
    char c;
    // checking for illegal chars
    while((c = fgetc(file)) != EOF )
    {
        if(c == ' ' || c == '\n')
            i++;
        else if(c > '9' || c < '0')
            return 0;
    }
    // checking if there is right amount of numbers
    if(i != (rows*cols))
        return 0;
    fclose(file);
    file = fopen(filepath,"r");
    if(file == NULL)
    {
        fputs("Opening file error\n", stderr);
        return -1;
    }
    fscanf(file,"%d %d",&rows, &cols);
    int nbr[rows*cols];
    int fnbr;
    // creating array with numbers from maze
    for(int i = 0; i < rows*cols; i++)
    {
        fscanf(file,"%d ", &fnbr);
        nbr[i] = fnbr;
    }
    fclose(file);
    int nbr1,nbr2;
    // cheking if there are right borders(only left and right)
    for(int j = 0; j < rows*cols; j++)
    {
        nbr2 = nbr[j];
        nbr2 = nbr2&7;
        if(j % cols == 0)
            nbr1 = -1;
        if(nbr1 > 0)
        {
            nbr1 = nbr1 / 2;
            if(nbr2 % 2 != nbr1 % 2)
                return 0;
        }
        nbr1 = nbr2;
    }
    int k = 1;
    int l = 0;
    int counter = 1;
    // checking if there are right borders(only up and down)
    if(cols % 2 == 1)
    {
        while(k + cols < rows*cols)
        {
            nbr1 = nbr[k];
            nbr1 = (nbr1 / 2) / 2;
            nbr2 = nbr[k + cols];
            nbr2 = (nbr2 / 2) / 2;
            if(nbr1 % 2 != nbr2 % 2)
                return 0;
            k = k + 2;
        }
    }
    else
    {
        while(k + cols < rows*cols)
        {
            l++;
            nbr1 = nbr[k];
            nbr1 = (nbr1 / 2) / 2;
            nbr2 = nbr[k + cols];
            nbr2 = (nbr2 / 2) / 2;
            if(nbr1 % 2 != nbr2 % 2)
                return 0;
            if(l == cols/2 && counter == 1)
            {
                k = k + 1; 
                l = 0;
                counter = 0;
            }
            else if(l == cols/2 && counter == 0)
            {
                k = k + 3;
                l = 0;
                counter = 1;
            }
            else 
                k = k + 2;
        }
    }
    // returning 1 if everything went smoothly
    return 1;
}

// looking for starting border in map
// also looking if starting position in maze is right
// returning 0 if not right pos
// returning RIGHT/LEFT/UP_DOWN if everything is all right
int start_border(Map *map, int r, int c, int leftright)
{
    if((map->cells[(r-1)*map->cols + c - 1] & 7) == 7)
        return 0;
    if (leftright == 1)
    {
        if(c == 1 && r == 1)
        {
            if(isborder(map,r,c,UP_DOWN))
            {
                if(isborder(map,r,c,LEFT))
                    return 0;
                return RIGHT;
            }
            return LEFT;
        }
        else if(r == 1 && c == map->cols)
        {
            if(c%2 == 0)
            {
                if(isborder(map,r,c,RIGHT))
                    return 0;
                return LEFT;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,RIGHT))
                        return 0;
                    return UP_DOWN;
                }
                return LEFT;
            }
        }
        else if(c == 1 && r == map->rows)
        {
            if(r%2 == 1)
            {
                if(isborder(map,r,c,LEFT))
                    return 0;
                return RIGHT;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,LEFT))
                        return 0;
                    return UP_DOWN;
                }
                return RIGHT;
            }
        }
        else if(c == map->cols && r == map->rows)
        {
            if((c+r)%2 == 0)
            {
                if(isborder(map,r,c,RIGHT))
                    return 0;
                return UP_DOWN;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,RIGHT))
                        return 0;
                    return LEFT;
                }
                return RIGHT;
            }
        }
        else if(c == 1 && r % 2 == 1)
        {
            if(isborder(map,r,c,LEFT))
                return 0;
            return RIGHT;
        }
        else if(c == 1 && r % 2 == 0)
        {
            if(isborder(map,r,c,LEFT))
                return 0;
            return UP_DOWN;
        }
        else if(r == 1)
        {
            if(c % 2 == 0)
                return 0;
            else if(isborder(map,r,c,UP_DOWN))
                return 0;
            return LEFT;
        }
        else if(r == map->rows)
        {
            if(c % 2 == 0)
                return 0;
            else if(isborder(map,r,c,UP_DOWN))
                return 0;
            return RIGHT;
        }
        else if(c == map->cols && r % 2 == 1)
        {
            if(isborder(map,r,c,RIGHT))
                return 0;
            if(c%2 == 1)
                return UP_DOWN;
            return LEFT;
        }
        else if(c == map->cols && r % 2 == 0)
        {
            if(isborder(map,r,c,RIGHT))
                return 0;
            if(c%2 == 1)
                return LEFT;
            return UP_DOWN;
        }
    }
    else
    {
        if(c == 1 && r == 1)
        {
            if(isborder(map,r,c,UP_DOWN))
            {
                if(isborder(map,r,c,LEFT))
                    return 0;
                return UP_DOWN;
            }
            return RIGHT;
        }
        else if(r == 1 && c == map->cols)
        {
            if(c%2 == 0)
            {
                if(isborder(map,r,c,RIGHT))
                    return 0;
                return UP_DOWN;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,RIGHT))
                        return 0;
                    return LEFT;
                }
                return RIGHT;
            }
        }
        else if(c == 1 && r == map->rows)
        {
            if(r%2 == 1)
            {
                if(isborder(map,r,c,LEFT))
                    return 0;
                return UP_DOWN;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,LEFT))
                        return 0;
                    return RIGHT;
                }
                return LEFT;
            }
        }
        else if(c == map->cols && r == map->rows)
        {
            if((c+r)%2 == 0)
            {
                if(isborder(map,r,c,RIGHT))
                    return 0;
                return LEFT;
            }
            else
            {
                if(isborder(map,r,c,UP_DOWN))
                {
                    if(isborder(map,r,c,RIGHT))
                        return 0;
                    return UP_DOWN;
                }
                return LEFT;
            }
        }
        else if(c == 1 && r % 2 == 1)
        {
            if(isborder(map,r,c,LEFT))
                return 0;
            return UP_DOWN;
        }
        else if(c == 1 && r % 2 == 0)
        {
            if(isborder(map,r,c,LEFT))
                return 0;
            return RIGHT;
        }
        else if(r == 1)
        {
            if(c % 2 == 0)
                return 0;
            else if(isborder(map,r,c,UP_DOWN))
                return 0;
            return RIGHT;
        }
        else if(r == map->rows)
        {
            if(c%2 == 0)
                return 0;
            else if(isborder(map,r,c,UP_DOWN))
                return 0;
            return LEFT;
        }
        else if(c == map->cols && r % 2 == 1)
        {
            if(isborder(map,r,c,RIGHT))
                return 0;
            if(c%2 == 1)
                return LEFT;
            return UP_DOWN;
        }
        else if(c == map->cols && r % 2 == 0)
        {
            if(isborder(map,r,c,RIGHT))
                return 0;
            if(c%2 == 1)
                return UP_DOWN;
            return LEFT;
        }
    }
    return 0;
}

// fill cells
void fill_cells(char *filepath, Map *Map)
{
    FILE *file;
    file = fopen(filepath,"r");
    if(file == NULL)
    {
        fputs("Opening file error\n", stderr);
        return;
    }
    int i = 0;
    int nbr = 0;
    fscanf(file,"%d %d",&nbr, &nbr);
    while(fscanf(file,"%d ", &nbr) != EOF)
    {
        Map->cells[i] = nbr;
        i++;
    }
    fclose(file);
}

// reserving memory for Map and creating Map
Map Map_ctor(int rows, int cols)
{
    Map Map;
    Map.cells = malloc(sizeof(int) * (rows * cols));
    if(Map.cells == NULL)
    {
        Map.rows = 0;
        Map.cols = 0;
        return Map;
    }
    else
    {  
        Map.rows = rows;
        Map.cols = cols;
    }
    return Map;
}

// free memory
void Map_dtor(Map *Map)
{
    free(Map->cells);    
}

int main(int argc, char *argv[])
{
    // first argument --help
    if(strcmp(argv[1],"--help") == 0)
    {
        if(argc != 2)
        {
            fputs("Error - too many or not enough values", stderr);
            return -1;
        }
        fputs("--test: it only checks that the file given by the second program argument contains the proper maze map definition. Prints Valid or Invalid. For example: --test maze.txt\n--rpath: searches for crossing through the maze at the entry on row R and column C. Searches for crossing using the right-hand rule. For example: --rpath 6 1 maze.txt\n--lpath: searches for crossing through the maze at the entry on row R and column C. Searches for crossing using the left-hand rule. For example: --lpath 6 1 maze.txt\n--shortest: looking for the shortest way out of the maze when entering R and Column C. For example: --shortest 6 1 maze.txt\n",stdout);
    }
    
    // first argument --test
    else if(strcmp(argv[1],"--test") == 0)
    {
        if(argc != 3)
        {
            fputs("Error - too many or not enough values", stderr);
            return -1;
        }
        char *filepath = argv[2];
        int test = test_func(filepath);
        if(test == 1)
            fputs("Valid\n", stdout);
        else
            fputs("Invalid\n", stdout);

    }
    
    // first argument --lpath or --rpath
    else if(strcmp(argv[1],"--rpath") == 0 || strcmp(argv[1],"--lpath") == 0)
    {
        if(argc != 5 )
        {
            fputs("Error - too many or not enough values\n", stderr);
            return -1;
        }
        
        // if right path 1 , if left path 0;
        int leftright;
        if(strcmp(argv[1],"--rpath") == 0)
            leftright = 1;
        else
            leftright = 0;
        
        // starting position
        int rows_st = char_to_int(argv[2]);
        int cols_st = char_to_int(argv[3]);
        
        // checking arguments
        if(rows_st <= 0 || cols_st <= 0)
        {
            fputs("Error - wrong starting values\n", stderr);
            return -1;
        }
        
        char *filepath = argv[4];
       
        // testing maze
        int test = test_func(filepath);
        if(test == 0)
        {
            fputs("Maze is invalid\n", stderr);
            return -1;
        }

        // getting rows and cols from file
        FILE *fp;
        fp = fopen(filepath,"r");
        if(fp == NULL)
        {
            fputs("Opening file error\n", stderr);
            return -1;
        }
        int rows, cols;
        fscanf(fp,"%d %d", &rows, &cols);
        fclose(fp);
        
        // creating Map
        Map Map = Map_ctor(rows, cols);
        
        // filling cells 
        fill_cells(filepath, &Map);

        // start = strating border
        // checking if strating position is right
        int start = start_border(&Map,rows_st,cols_st,leftright);
        if(start == 0)
        {
            fputs("wrong starting position\n", stderr);
            // freeing Map
            Map_dtor(&Map);
            return -1;
        }
        int l_rows = rows_st;
        int l_cols = cols_st;
        printf("%d,%d\n",rows_st,cols_st);
        
        // first move
        first_move(&rows_st,&cols_st,start,leftright, &Map);

        // all moves
        while(rows_st >= 1 && rows_st <= rows && cols_st >= 1 && cols_st <= cols)
        {
            printf("%d,%d\n",rows_st,cols_st);
            int up_down = up_down_triangle(rows_st,cols_st);
            if(up_down == DOWN)
            {
                if(isborder(&Map,rows_st,cols_st,UP_DOWN) && !(isborder(&Map,rows_st,cols_st,RIGHT) || isborder(&Map,rows_st,cols_st,LEFT)))
                {
                    l_rows = rows_st;
                    if(l_rows == rows_st && l_cols == --cols_st)
                    {
                        l_cols = cols_st + 1;
                        cols_st += 2;
                    }
                    else
                        l_cols = cols_st + 1;
                }
                else if(isborder(&Map,rows_st,cols_st,RIGHT) && !(isborder(&Map,rows_st,cols_st,UP_DOWN) || isborder(&Map,rows_st,cols_st,LEFT)))
                {
                    l_cols = cols_st;
                    if(l_rows == --rows_st && l_cols == cols_st)
                    {
                        l_rows = rows_st + 1;
                        rows_st++;
                        cols_st--;
                    }
                    else
                        l_rows = rows_st + 1;
                }
                else if(isborder(&Map,rows_st,cols_st,LEFT) && !(isborder(&Map,rows_st,cols_st,UP_DOWN) || isborder(&Map,rows_st,cols_st,RIGHT)))
                {
                    l_rows = rows_st;
                    if(l_rows == rows_st && l_cols == ++cols_st)
                    {
                        l_cols = cols_st - 1;
                        cols_st--;
                        rows_st--;
                    }
                    else
                        l_cols = cols_st - 1;
                }
                else if ((isborder(&Map,rows_st,cols_st,UP_DOWN) && isborder(&Map,rows_st,cols_st,RIGHT)) || (isborder(&Map,rows_st,cols_st,UP_DOWN) && isborder(&Map,rows_st,cols_st,LEFT)) || (isborder(&Map,rows_st,cols_st,LEFT) && isborder(&Map,rows_st,cols_st,RIGHT)))
                {
                    int a;
                    a = l_rows;
                    l_rows = rows_st;
                    rows_st = a;
                    a = l_cols;
                    l_cols = cols_st;
                    cols_st = a;
                }
                else 
                {
                    if(leftright == 1)
                    {
                        if(isborder(&Map,rows_st,cols_st + 1,UP_DOWN) && (cols_st + 1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st++;
                        }
                        else if(isborder(&Map,rows_st,cols_st - 1,LEFT) && (cols_st -1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st--;
                        }
                        else if(isborder(&Map,rows_st - 1,cols_st,RIGHT) && (rows_st -1) != l_rows)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            rows_st--;
                        }
                        else
                        {
                            fputs("Something went wrong", stderr);
                            // freeing Map
                            Map_dtor(&Map);
                            return -1;
                        }
                    }
                    else
                    {
                        if(isborder(&Map,rows_st,cols_st + 1,RIGHT) && (cols_st + 1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st++;
                        }
                        else if(isborder(&Map,rows_st,cols_st - 1,UP_DOWN) && (cols_st -1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st--;
                        }
                        else if(isborder(&Map,rows_st - 1,cols_st,LEFT) && (rows_st -1) != l_rows)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            rows_st--;
                        }
                        else
                        {
                            fputs("Something went wrong", stderr);
                            // freeing Map
                            Map_dtor(&Map);
                            return -1;
                        }
                    }
                }
            }
            else
            {
                if(isborder(&Map,rows_st,cols_st,UP_DOWN) && !(isborder(&Map,rows_st,cols_st,LEFT) || isborder(&Map,rows_st,cols_st,RIGHT)))
                {
                    l_rows = rows_st;
                    if(l_rows == rows_st && l_cols == ++cols_st)
                    {
                        l_cols = cols_st - 1;
                        cols_st -= 2;
                    }
                    else
                        l_cols = cols_st - 1;
                }
                else if(isborder(&Map,rows_st,cols_st,RIGHT) && !(isborder(&Map,rows_st,cols_st,UP_DOWN) || isborder(&Map,rows_st,cols_st,LEFT)))
                {
                    l_rows = rows_st;
                    if(l_rows == rows_st && l_cols == --cols_st)
                    {
                        l_cols = cols_st + 1;
                        cols_st++;
                        rows_st++;
                    }
                    else
                        l_cols = cols_st + 1;
                }
                else if(isborder(&Map,rows_st,cols_st,LEFT) && !(isborder(&Map,rows_st,cols_st,RIGHT) || isborder(&Map,rows_st,cols_st,UP_DOWN)))
                {
                    l_cols = cols_st;
                    if(l_rows == ++rows_st && l_cols == cols_st)
                    {
                        l_rows = rows_st - 1;
                        rows_st--;
                        cols_st++;
                    }
                    else
                        l_rows = rows_st - 1;
                }
                else if ((isborder(&Map,rows_st,cols_st,UP_DOWN) && isborder(&Map,rows_st,cols_st,RIGHT)) || (isborder(&Map,rows_st,cols_st,UP_DOWN) && isborder(&Map,rows_st,cols_st,LEFT)) || (isborder(&Map,rows_st,cols_st,LEFT) && isborder(&Map,rows_st,cols_st,RIGHT)))
                {
                    int a;
                    a = l_rows;
                    l_rows = rows_st;
                    rows_st = a;
                    a = l_cols;
                    l_cols = cols_st;
                    cols_st = a;
                }
                else 
                {
                    if(leftright == 1)
                    {
                        if(isborder(&Map,rows_st,cols_st + 1,RIGHT) && (cols_st + 1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st++;
                        }
                        else if(isborder(&Map,rows_st,cols_st - 1,UP_DOWN) && (cols_st -1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st--;
                        }
                        else if(isborder(&Map,rows_st + 1,cols_st,LEFT) && (rows_st +1) != l_rows)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            rows_st++;
                        }
                        else
                        {
                            fputs("Something went wrong", stderr);
                            // freeing Map
                            Map_dtor(&Map);
                            return -1;
                        }
                    }
                    else
                    {
                        if(isborder(&Map,rows_st,cols_st + 1,UP_DOWN) && (cols_st + 1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st++;
                        }
                        else if(isborder(&Map,rows_st,cols_st - 1,LEFT) && (cols_st -1) != l_cols)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            cols_st--;
                        }
                        else if(isborder(&Map,rows_st + 1,cols_st,RIGHT) && (rows_st +1) != l_rows)
                        {
                            l_rows = rows_st;
                            l_cols = cols_st;
                            rows_st++;
                        }
                        else
                        {
                            fputs("Something went wrong", stderr);
                            // freeing Map
                            Map_dtor(&Map);
                            return -1;
                        }
                    }
                }
            }
        }
    
        // freeing Map
        Map_dtor(&Map);
    }
    
    // first argument --shortest
    else if(strcmp(argv[1],"--shortest") == 0)
        fputs("Not working\n", stdout);
    
    return 0;
}