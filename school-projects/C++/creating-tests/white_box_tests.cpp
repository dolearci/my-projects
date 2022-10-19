//======== Copyright (c) 2017, FIT VUT Brno, All rights reserved. ============//
//
// Purpose:     White Box - Tests suite
//
// $NoKeywords: $ivs_project_1 $white_box_code.cpp
// $Author:     Daniel Olearcin <xolear00@stud.fit.vutbr.cz>
// $Date:       $2020-02-29
//============================================================================//
/**
 * @file white_box_tests.cpp
 * @author Daniel Olearcin
 * 
 * @brief Implementace testu prace s maticemi.
 */

#include "gtest/gtest.h"
#include "white_box_code.h"

using namespace std;
using namespace ::testing;

class TMatrix : public Test
{
    protected:
        vector<vector<double>> one{{1}};
        vector<vector<double>> onetwo{{1,2}};
        vector<vector<double>> twoone{{2},{3}};
        vector<vector<double>> twotwo{{1,20},{3,4}};
        vector<vector<double>> threethree{{1,2,3},{4,5,6},{7,8,100}};
        vector<vector<double>> det01{{0}};
        vector<vector<double>> det02{{3,2},{6,4}};
        vector<vector<double>> twotwomin{{-4,-2},{-5,-3}};
        vector<vector<double>> fourfour{{1,2,3,4},{3,200,5,6},{5,7,8,100},{1,2,3,4}};
};

TEST_F(TMatrix,Constructor)
{
    EXPECT_NO_THROW(Matrix());
    EXPECT_ANY_THROW(Matrix(-1,4));
    EXPECT_ANY_THROW(Matrix(0,4));
    EXPECT_NO_THROW(Matrix(4,7));
}
TEST_F(TMatrix,Destruktor)
{
    Matrix *TMatrix = new Matrix(5,2);
    

}
TEST_F(TMatrix,set)
{
    Matrix *TMatrix = new Matrix(5,2);
    EXPECT_NO_THROW(TMatrix->set(1,1,3));
    EXPECT_NO_THROW(TMatrix->set(0,0,-3));
    EXPECT_TRUE(TMatrix->set(-0,1,3));
    EXPECT_FALSE(TMatrix->set(-1,1,3));
    EXPECT_NO_THROW(TMatrix->set(1,1,-3.5));
    EXPECT_FALSE(TMatrix->set(threethree));
    TMatrix = new Matrix(3,3);
    EXPECT_TRUE(TMatrix->set(threethree));
}
TEST_F(TMatrix,get)
{
    Matrix *TMatrix = new Matrix(3,3);
    TMatrix->set(threethree);
    EXPECT_EQ(TMatrix->get(2,2),threethree[2][2]);
    EXPECT_ANY_THROW(TMatrix->get(-2,2));
    EXPECT_ANY_THROW(TMatrix->get(4,3));
    EXPECT_NO_THROW(TMatrix->get(0,0));
    TMatrix = new Matrix(2,2);
    TMatrix->set(twotwo);
    for(int i = 0; i < twotwo.size(); i++)
    {
        for(int j = 0; j < twotwo.size(); j++)
        {
            EXPECT_EQ(TMatrix->get(i,j),twotwo[i][j]);
        }
    }
}
TEST_F(TMatrix,equal)
{
    Matrix *TMatrix = new Matrix(3,3);
    TMatrix->set(threethree);
    EXPECT_FALSE(TMatrix->operator==(Matrix(3,3)));
    EXPECT_NO_THROW(TMatrix->operator==(Matrix(3,3)));
    TMatrix->set(twotwomin);
    EXPECT_FALSE(TMatrix->operator==(Matrix(3,3)));
    EXPECT_NO_THROW(TMatrix->operator==(Matrix(3,3)));
    EXPECT_ANY_THROW(TMatrix->operator==(Matrix(1,1)));
    EXPECT_ANY_THROW(TMatrix->operator==(Matrix(-3,4)));
}
TEST_F(TMatrix,plus)
{
    Matrix *TMatrix = new Matrix();
    EXPECT_NO_THROW(TMatrix->operator+(Matrix()));
    EXPECT_TRUE(TMatrix->operator==(Matrix()));
    TMatrix = new Matrix(2,1);
    TMatrix->set(twoone);
    EXPECT_ANY_THROW(TMatrix->operator+(Matrix(5,5)));
    EXPECT_ANY_THROW(TMatrix->operator+(Matrix(2,2)));
    EXPECT_ANY_THROW(TMatrix->operator+(Matrix(0,-1)));
    Matrix *TTMatrix = new Matrix(2,2);
    TTMatrix->set(twotwomin);
    EXPECT_ANY_THROW(TMatrix->operator+(*TTMatrix));
}
TEST_F(TMatrix,multiply)
{
    Matrix *TMatrix = new Matrix();
    EXPECT_NO_THROW(TMatrix->operator*(Matrix()));
    EXPECT_TRUE(TMatrix->operator==(Matrix()));
    TMatrix = new Matrix(2,1);
    TMatrix->set(twoone);
    EXPECT_ANY_THROW(TMatrix->operator*(Matrix(2,1)));
    EXPECT_ANY_THROW(TMatrix->operator*(Matrix(2,2)));
    EXPECT_NO_THROW(TMatrix->operator*(Matrix(1,2)));
    EXPECT_NO_THROW(TMatrix->operator*(0));
    EXPECT_NO_THROW(TMatrix->operator*(2));
    EXPECT_NO_THROW(TMatrix->operator*(-4));
    EXPECT_EQ(TMatrix->operator*(0),Matrix(2,1));
}
TEST_F(TMatrix,solve)
{
    Matrix *TMatrix = new Matrix(2,2);
    TMatrix->set(twotwo);
    vector<double> b;
    vector<double> results;
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    b = {-2,3};
    EXPECT_NO_THROW(TMatrix->solveEquation(b));
    b = {0,0,6};
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    b = {0,0};
    EXPECT_NO_THROW(TMatrix->solveEquation(b));
    b = {-2,-4};
    EXPECT_NO_THROW(TMatrix->solveEquation(b));
    TMatrix = new Matrix(2,2);
    TMatrix->set(det02);
    b = {3,-5};
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    b = {0,0};
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    TMatrix = new Matrix(1,2);
    TMatrix->set(onetwo);
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    b = {3,4,5};
    TMatrix = new Matrix(3,3);
    TMatrix->set(threethree);
    EXPECT_NO_THROW(TMatrix->solveEquation(b));
    b = {1,5,6,7};
    TMatrix = new Matrix(4,4);
    TMatrix->set(fourfour);
    EXPECT_ANY_THROW(TMatrix->solveEquation(b));
    TMatrix = new Matrix(1,1);
    TMatrix->set(one);
    b = {1};
    EXPECT_NO_THROW(TMatrix->solveEquation(b));
}








/*** Konec souboru white_box_tests.cpp ***/
