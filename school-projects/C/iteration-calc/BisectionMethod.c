/**
 * Projekt 2. (Iterační výpočty)
 * Daniel Olearčin <xolear00@stud.fit.vutbr.cz>
 */

#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <math.h>

#define I_0 1e-12
#define U_t 25.8563e-3

// solving
double solve(double U_0, double U_p, double R)
{
    return I_0*(exp(U_p/U_t)-1) - ((U_0 - U_p)/R);
}
// converting string to double 
double  str_to_double(char *str)
{
    char *ptr = NULL;
    double nbr = strtod(str, &ptr);
    if(*ptr != '\0' || str[0] == '\0')
        return -1;
    return nbr;
}

// calculating u_mid (bisection method)
double diode(double u0, double r, double eps)
{
    double u_start = 0.0;
    double u_end = u0;
    double u_mid;
    double U_p = 0;
    double old_U_p;
    int counter = 0;
    int end = 40;
    
    // loop for bisection method
    // number of iterations stable
    while(fabs(u_end - u_start) > eps && counter < end)
    {
        counter++;
        old_U_p = U_p;
        u_mid = (u_start + u_end) / 2;
        U_p = solve(u0,u_mid,r);
        // if value is still same return u_mid
        if(old_U_p == U_p) 
        {
            return u_mid;
        }
        else
        {
            if(solve(u0,u_start,r) * U_p > 0)
            {
                u_start = u_mid;
            }
            else
            {
                u_end = u_mid;
            }
        }
    }   
    return u_mid;
   

}

int main(int argc, char *argv[])
{
    // checking number of arguments
    if(argc != 4)
    {
        fputs("Error - Missing or too many arguments\n",stderr);
        return -1;
    }
    
    double U_0 = str_to_double(argv[1]);
    double R = str_to_double(argv[2]);
    double EPS = str_to_double(argv[3]);
    
    // checking if arguments have normal values
    if(U_0 <= 0.0  || R <= 0.0|| EPS <= 0.0)
    {
        fputs("ERROR - Wrong values\n", stderr);
        return -1;
    }
    double U_p = diode(U_0, R, EPS);
    double I_p = I_0*(exp(U_p/U_t)-1);
    
    // print on stdout
    printf("Up=%g V\nIp=%g A\n", U_p, I_p);        
    
    
    return 0;
}