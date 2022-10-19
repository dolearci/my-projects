#!/bin/sh

###############################
### Author: Daniel Olear?in ###
### IOS - 1.project         ###
### VUT FIT Brno            ###
### 26.3.2020               ###
###############################


## Wrong variables
set -u

## Inicialization##############################################
POSIXLY_CORRECT=yes
Error_output="Usage: ./dirgraph.sh [-i FILE_ERE] [-n] [DIR]"
Search_dir=$(pwd)
Directories=0
Files=0
Ignoration=0
Ignored_reg=0
Normalization=0
width=`tput cols`
###############################################################

## File size ##
FS100B=0
FS1KiB=0
FS10KiB=0
FS100KiB=0
FS1MiB=0
FS10MiB=0
FS100MiB=0
FS1GiB=0
FSover1GiB=0
Max_size=0
Norm_size=0
###############

### Functions ###################
error()
{
    echo Bad usage.
    echo $Error_output >&2
    exit 1
}

## 3 args
Output()
{
    echo Root directory:" $1"
    echo Directories:" $2"
    echo All files:" $3"
    echo File size histogram:
}

## 1 arg
Hashes()
{
    if [ "$1" -ne 0 ] ; then
        for i in `seq 1 $1` ; do
            echo -n "#"
        done
    fi
    echo ""
}
#################################

## Controling arguments #################################################
if [ $# -ne 0 ] ; then
	while getopts "ni:" opt
	do
		case $opt in
		n)
			Normalization=1
			;;
		i)
            Ignoration=1
            Ignored_reg=$OPTARG
			;;
		esac
	done
fi

if [ $Ignoration -eq 1 ] ; then
    if [ "$Ignored_reg"  = "0" ] ; then
        error
    fi
fi

shift $(($OPTIND - 1))

if [ $# -eq 1 ] ; then
    if [ $Ignored_reg != $1 ] ; then
        cd $1 || {
            echo "Cannot change or directory $1 not exists" >&2
            exit 1
        }
    else
        echo "Ignored directory cannot be same as root directory" >&2
        exit 1
    fi
    Search_dir=$(pwd)
fi


if [ $# -gt 1 ] ; then
    error
fi

if [ $Ignored_reg  = "-n" ] ; then
    error
fi
#########################################################################

### Getting number of directories and files #####################################################
if [ $Ignoration -eq 1 ] ; then
    Directories=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type d | wc -l)
    Files=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f | wc -l) 
else
    Directories=$(find . -not -name '.*' -type d | wc -l)
    Files=$(find . -not -name '.*' -type f | wc -l)
fi
##################################################################################################

### Getting file sizes ####################################################################################################
if [ $Ignoration -eq 0 ] ; then
    Ignored_reg="<Direcitory or file with this cannot exists>?:*"
fi
FS100B=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size -100c | wc -l)
Max_size=$FS100B
FS1KiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +99c -size -1024c | wc -l)
if [ $FS1KiB -ge $Max_size ] ; then
    Max_size=$FS1KiB
fi
FS10KiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +1023c -size -10240c | wc -l)
if [ $FS10KiB -ge $Max_size ] ; then
    Max_size=$FS10KiB
fi
FS100KiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +10239c -size -102400c | wc -l)
if [ $FS100KiB -ge $Max_size ] ; then
    Max_size=$FS100KiB
fi
FS1MiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +102399c -size -1048576c | wc -l)
if [ $FS1MiB -ge $Max_size ] ; then
    Max_size=$FS1MiB
fi
FS10MiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +1048575c -size -10485760c | wc -l)
if [ $FS10MiB -ge $Max_size ] ; then
    Max_size=$FS10MiB
fi
FS100MiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +10485759c -size -104857600c | wc -l)
if [ $FS100MiB -ge $Max_size ] ; then
    Max_size=$FS100MiB
fi
FS1GiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +104857599c -size -1073741824c | wc -l)
if [ $FS1GiB -ge $Max_size ] ; then
    Max_size=$FS1GiB
fi
FSover1GiB=$(find . -depth -not \( -path "*$Ignored_reg*" -o -name '.*' \) -type f -size +1073741823c | wc -l)
if [ $FSover1GiB -ge $Max_size ] ; then
    Max_size=$FSover1GiB
fi
############################################################################################################################

### Normalization #########################################
if [ $Max_size -eq 0 ] ; then
    Normalization=0
fi

if [ $Normalization -eq 1 ] ; then
    width=`expr $width - 12`
    if [ $Max_size -ge $width ] ; then
        FS100B=`expr $FS100B \* 1000`
        FS1KiB=`expr $FS1KiB \* 1000`
        FS10KiB=`expr $FS10KiB \* 1000`
        FS100KiB=`expr $FS100KiB \* 1000`
        FS1MiB=`expr $FS1MiB \* 1000`
        FS10MiB=`expr $FS10MiB \* 1000`
        FS100MiB=`expr $FS100MiB \* 1000`
        FS1GiB=`expr $FS1GiB \* 1000`
        FSover1GiB=`expr $FSover1GiB \* 1000`
        Max_size=`expr $Max_size \* 1000`
        Norm_size=`expr $Max_size / $width`
        FS100B=`expr $FS100B / $Norm_size`
        FS1KiB=`expr $FS1KiB / $Norm_size`
        FS10KiB=`expr $FS10KiB / $Norm_size`
        FS100KiB=`expr $FS100KiB / $Norm_size`
        FS1MiB=`expr $FS1MiB / $Norm_size`
        FS10MiB=`expr $FS10MiB / $Norm_size`
        FS100MiB=`expr $FS100MiB / $Norm_size`
        FS1GiB=`expr $FS1GiB / $Norm_size`
        FSover1GiB=`expr $FSover1GiB / $Norm_size`
    else
        width=`expr $width \* 1000`
        Norm_size=`expr $width / $Max_size`
        FS100B=`expr $FS100B \* $Norm_size`
        FS100B=`expr $FS100B / 1000`
        FS1KiB=`expr $FS1KiB \* $Norm_size`
        FS1KiB=`expr $FS1KiB / 1000`
        FS10KiB=`expr $FS10KiB \* $Norm_size`
        FS10KiB=`expr $FS10KiB / 1000`
        FS100KiB=`expr $FS100KiB \* $Norm_size`
        FS100KiB=`expr $FS100KiB / 1000`
        FS1MiB=`expr $FS1MiB \* $Norm_size`
        FS1MiB=`expr $FS1MiB / 1000`
        FS10MiB=`expr $FS10MiB \* $Norm_size`
        FS10MiB=`expr $FS10MiB / 1000`
        FS100MiB=`expr $FS100MiB \* $Norm_size`
        FS100MiB=`expr $FS100MiB / 1000`
        FS1GiB=`expr $FS1GiB \* $Norm_size`
        FS1GiB=`expr $FS1GiB / 1000`
        FSover1GiB=`expr $FSover1GiB \* $Norm_size`
        FSover1GiB=`expr $FSover1GiB / 1000`
    fi
fi
##########################################################

### Output ######################################
Output "$Search_dir" "$Directories" "$Files"
echo -n " <100 B  : " 
Hashes "$FS100B"
echo -n " <1 KiB  : " 
Hashes "$FS1KiB"
echo -n " <10 KiB : " 
Hashes "$FS10KiB"
echo -n " <100 KiB: " 
Hashes "$FS100KiB"
echo -n " <1 MiB  : " 
Hashes "$FS1MiB"
echo -n " <10 MiB : " 
Hashes "$FS10MiB"
echo -n " <100 MiB: " 
Hashes "$FS100MiB"
echo -n " <1 GiB  : " 
Hashes "$FS1GiB"
echo -n " >=1 GiB : " 
Hashes "$FSover1GiB"
#################################################