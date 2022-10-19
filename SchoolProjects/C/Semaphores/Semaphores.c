/*******************************
 *** Author: Daniel Olearcin ***
 *** IOS - 2.projekt         ***
 *** VUT FIT Brno            ***
 *** 22.4.2020               ***
 *******************************/

#include <stdio.h>
#include <stdlib.h>
#include <stdbool.h>
#include <ctype.h>
#include <string.h>
#include <sys/mman.h>
#include <semaphore.h>
#include <sys/types.h>
#include <unistd.h>
#include <sys/stat.h>
#include <sys/shm.h>
#include <fcntl.h>
#include <signal.h>
#include <sys/wait.h>


#define random_sleep(max) {if (max == 0) usleep(0); else usleep((rand() % max) * 1000); }

FILE *output;
sem_t *sem_judge = NULL;
sem_t *sem_judge2 = NULL;
sem_t *sem_end = NULL;
sem_t *sem_confirmation = NULL;
sem_t *sem_counter= NULL;
sem_t *sem_certificate = NULL;

int *NE = NULL;
int *NC = NULL;
int *NB = NULL;
int *A = NULL;
int *I = NULL;
int *C = NULL;
int *B = NULL;
int *CNT_start = NULL;


//Function for opening file and checking that everything went smoothly
bool open_output()
{
    if((output = fopen("proj2.out", "w")) == NULL) 
    {
        fputs("Error - Problem with opening file\n", stderr);
        return false;
    }
    return true;

}

//Function for initialization
bool init(int argc)
{
    // controlling number of arguments
    if (argc != 6)
    {
        fputs("Error - Bad number of arguments\n", stderr);
        return false;
    }

    //initializing semaphores
    if((sem_judge = sem_open("/olear00_ios_proj2_judge", O_CREAT | O_EXCL, 0666, 1)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    if((sem_judge2 = sem_open("/olear00_ios_proj2_judge2", O_CREAT | O_EXCL, 0666, 1)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    if((sem_certificate = sem_open("/olear00_ios_proj2_certificate", O_CREAT | O_EXCL, 0666, 0)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    if((sem_counter = sem_open("/olear00_ios_proj2_counter", O_CREAT | O_EXCL, 0666, 1)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    if((sem_confirmation = sem_open("/olear00_ios_proj2_confirmation", O_CREAT | O_EXCL, 0666, 0)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    if((sem_end = sem_open("/olear00_ios_proj2_end", O_CREAT | O_EXCL, 0666, 0)) == SEM_FAILED) 
    {
        fputs("Error - semaphore went badly", stderr);
        return false;
    }
    
    //initializing variables
    NE = mmap(NULL, sizeof(*(NE)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    NC = mmap(NULL, sizeof(*(NC)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    NB = mmap(NULL, sizeof(*(NB)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    A = mmap(NULL, sizeof(*(A)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    I = mmap(NULL, sizeof(*(I)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    C = mmap(NULL, sizeof(*(C)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    B = mmap(NULL, sizeof(*(B)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    CNT_start = mmap(NULL, sizeof(*(B)), PROT_READ | PROT_WRITE, MAP_SHARED | MAP_ANONYMOUS, -1, 0);
    
    return true;
}

//Clearing and closing
void de_init()
{
    // unlink and closing all semaphores
    sem_close(sem_judge);
    sem_unlink("/olear00_ios_proj2_judge");
    sem_close(sem_end);
    sem_unlink("/olear00_ios_proj2_end");
    sem_close(sem_judge2);
    sem_unlink("/olear00_ios_proj2_judge2");
    sem_close(sem_certificate);
    sem_unlink("/olear00_ios_proj2_certificate");
    sem_close(sem_counter);
    sem_unlink("/olear00_ios_proj2_counter");
    sem_close(sem_confirmation);
    sem_unlink("/olear00_ios_proj2_confirmation");

    //unlinking variables
    munmap(NE, sizeof(NE));
    munmap(NC, sizeof(NC));
    munmap(NB, sizeof(NB));
    munmap(A, sizeof(A));
    munmap(I, sizeof(I));
    munmap(C, sizeof(C));
    munmap(B, sizeof(B));
    munmap(CNT_start, sizeof(CNT_start));
}

//Function for controlling arguments
bool control_arguments(int PI,int IG, int JG, int IT, int JT)
{
    if (PI < 1 || IG < 0 || IG > 2000 || JG < 0 || JG > 2000 || IT < 0 || IT > 2000 || JT < 0 || JT > 2000)
    {
        fputs("Error - Bad values of arguments\n", stderr);
        return false;
    }
    return true;
}

//Function that turn string to int 
int str_to_int(char *str)
{
    char *ptr = NULL;
    int nbr = strtol(str, &ptr, 0);
    if(*ptr != '\0' || str[0] == '\0')
        return -1;
    return nbr;
}

//Function judge
void f_judge(int JT,int PI, int JG)
{   
    while(*I != -1)
    {
        if(JG <= 1) sleep(1);
        else random_sleep(JG);
        sem_trywait(sem_judge);
        sem_trywait(sem_confirmation);
        sem_trywait(sem_certificate);
        sem_wait(sem_counter);
        int cnt_B = *NB;
        *B = cnt_B;
        int cnt_C = *I;
        sem_post(sem_counter);
        sem_wait(sem_counter);
        fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: wants to enter\n", (*A)++);
        sem_post(sem_counter);
        sem_wait(sem_counter);
        fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: enters\t\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, *NE, *NC, *NB);
        sem_post(sem_counter);
        sem_wait(sem_counter);
        if(*NE != *NC)
        {
            fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: waits for imm\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, *NE, *NC, *NB);
            sem_post(sem_counter);
            usleep(5000);
        }
        else sem_post(sem_counter);
        sem_wait(sem_counter);
        fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: starts confirmation\t\t\t: %d\t: %d\t: %d\n", (*A)++, *NE, *NC, *NB);
        sem_post(sem_counter);
        random_sleep(JT);
        sem_wait(sem_counter);
        fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: ends confirmation\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, 0, 0, *NB);
        sem_post(sem_counter);
        int cnt_NC = *NB;
        *C = cnt_NC;
        if(*NC != 0) sem_post(sem_confirmation);
        random_sleep(JT);
        *NE = 0;
        *NC = 0;
        sem_wait(sem_counter);
        fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: leaves\t\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, *NE, *NC, *NB);
        sem_post(sem_counter);
        for(int i = 0; i < *B; i++) sem_post(sem_certificate);
        if(*I == PI && cnt_C == PI)
        {
            sem_wait(sem_counter);
            fprintf(output,"%d\t\t: JUDGE\t\t\t\t\t\t: finishes\n", (*A)++);
            sem_post(sem_counter);
            *I = -1;
            sem_post(sem_end);
        }
        sem_post(sem_judge);
    }
    exit(0);
}
// Function immi start and certifikate
void process_immi_start(int IT,int IG)
{
    random_sleep(IG);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: starts\n", (*A)++, ++(*CNT_start));
    int cnt = *CNT_start;
    sem_post(sem_counter);
    sem_wait(sem_judge);
    sem_post(sem_judge);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: enters\t\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, ++(*I), ++(*NE), *NC, ++(*NB));
    sem_post(sem_counter);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: checks\t\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, *I, *NE, ++(*NC), *NB);
    sem_post(sem_counter);
    sem_wait(sem_confirmation);
    if(--(*C) != 0) sem_post(sem_confirmation);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: wants certificate\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, cnt, *NE, *NC, *NB);
    sem_post(sem_counter);
    random_sleep(IT);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: got certificate\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, cnt, *NE, *NC, *NB);
    sem_post(sem_counter);
    sem_wait(sem_certificate);
    sem_wait(sem_counter);
    fprintf(output,"%d\t\t: IMM %d\t\t\t\t\t\t: leaves\t\t\t\t\t\t: %d\t: %d\t: %d\n", (*A)++, cnt, *NE, *NC, --(*NB));
    sem_post(sem_counter);
    exit(0);
}

int main(int argc, char *argv[])
{   
    //Initialization and controlling number of args
    if(! init(argc)) 
    {
        de_init();
        return 1;
    }
    
    //Getting arguments
    int PI = str_to_int(argv[1]);
    int IG = str_to_int(argv[2]);
    int JG = str_to_int(argv[3]);
    int IT = str_to_int(argv[4]);
    int JT = str_to_int(argv[5]);

    if(! control_arguments(PI, IG, JG, IT, JT)) 
    {
        de_init();
        return 1;
    }
    if(! open_output()) 
    {
        de_init();
        return 1;
    }
    *A = 1;
    setbuf(output, NULL);

    //Creating processes
    pid_t judge = fork();
    if(judge == 0)
    {
        f_judge(JT, PI,JG);
    }
    else if(judge < 0)
    {
        fputs("Error - fork went badly", stderr);
        de_init();
        return 1;
    }
    else
    {
        for(int i = 0; i < PI;)
        {
            i = i + 1;
            pid_t immigrant = fork();
            if(immigrant == 0)
            {
                process_immi_start(IT,IG);
            }
            else if(immigrant < 0)
            {
                fputs("Error - fork went badly", stderr);
                de_init();
                return 1;
            }
        }
    }
    sem_wait(sem_end);
    waitpid(judge,NULL,0);
    de_init();
    fclose(output);
    return 0;
}