//======== Copyright (c) 2017, FIT VUT Brno, All rights reserved. ============//
//
// Purpose:     Red-Black Tree - public interface tests
//
// $NoKeywords: $ivs_project_1 $black_box_tests.cpp
// $Author:     Daniel Olearcin <xolear00@stud.fit.vutbr.cz>
// $Date:       $2020-02-29
//============================================================================//
/**
 * @file black_box_tests.cpp
 * @author Daniel Olearcin
 * 
 * @brief Implementace testu binarniho stromu.
 */

#include <vector>
#include "gtest/gtest.h"
#include "red_black_tree.h"
using namespace std;
using namespace ::testing;

class Empty : public Test
{
    protected:
        BinaryTree *empty;
        virtual void SetUp()
        {
            empty = new BinaryTree();
        }
        virtual void TearDown()
        {
            delete empty;
        }
};

class Nempty : public Test
{
    protected:
        BinaryTree *nempty;
        virtual void SetUp()
        {
            nempty = new BinaryTree();
            int node[13] = {1,9,3,7,11,19,14,26,15,41,22,52,97};
            int len = 13;
            for(int i = 0; i < len; i++)
            {
                nempty->InsertNode(node[i]);
            }
        }
        virtual void TearDown()
        {
            delete nempty;
        }
};

class Axioms : public Test
{
    protected:
        BinaryTree *axioms;
        virtual void SetUp()
        {
            axioms = new BinaryTree();
            int node[13] = {1,9,3,7,11,19,14,26,15,41,22,52,97};
            int len = 13;
            for(int i = 0; i < len; i++)
            {
                axioms->InsertNode(node[i]);
            }
        }
        virtual void TearDown()
        {
            delete axioms;
        }
};

TEST_F(Empty, insertN)
{
    pair<bool, struct Node_t*> insert1;
    pair<bool, struct Node_t*> insert2;
    pair<bool, struct Node_t*> insert3;
    struct Node_t* null = NULL;
    insert1 = empty->InsertNode(1);
    EXPECT_NE(insert1.second,null);
    EXPECT_TRUE(insert1.first);
    insert2 = empty->InsertNode(1);
    EXPECT_EQ(insert1.second,insert2.second);
    EXPECT_FALSE(insert2.first);
    insert3 = empty->InsertNode(-1);
    EXPECT_NE(insert1.second,insert3.second);
    EXPECT_NE(insert2.second,insert3.second);
    EXPECT_TRUE(insert3.first);
}

TEST_F(Empty, deleteN)
{
    EXPECT_FALSE(empty->DeleteNode(22));
    EXPECT_FALSE(empty->DeleteNode(-32));
    EXPECT_FALSE(empty->DeleteNode(24.56));
    EXPECT_FALSE(empty->DeleteNode(0));
}

TEST_F(Empty, findN)
{
    struct Node_t* null = NULL;
    EXPECT_EQ(empty->FindNode(9), null);
    EXPECT_EQ(empty->FindNode(-2), null);
    EXPECT_EQ(empty->FindNode(137), null);
    EXPECT_EQ(empty->FindNode(22), null);
    EXPECT_EQ(empty->FindNode(22.222), null);
}

TEST_F(Nempty, insertN)
{
    pair<bool, struct Node_t*> insert1;
    pair<bool, struct Node_t*> insert2;
    struct Node_t* null = NULL;
    insert1 = nempty->InsertNode(2);
    EXPECT_NE(insert1.second,null);
    EXPECT_TRUE(insert1.first);
    insert2 = nempty->InsertNode(9);
    EXPECT_NE(insert2.second,null);
    EXPECT_FALSE(insert2.first);
}

TEST_F(Nempty, deleteN)
{
    EXPECT_TRUE(nempty->DeleteNode(9));
    EXPECT_FALSE(nempty->DeleteNode(-2));
    EXPECT_FALSE(nempty->DeleteNode(137));
    EXPECT_TRUE(nempty->DeleteNode(11));
    EXPECT_FALSE(nempty->DeleteNode(20));
}

TEST_F(Nempty, findN)
{
    struct Node_t* null = NULL;
    EXPECT_NE(nempty->FindNode(9), null);
    EXPECT_EQ(nempty->FindNode(-2), null);
    EXPECT_EQ(nempty->FindNode(137), null);
    EXPECT_EQ(nempty->FindNode(23), null);
    EXPECT_NE(nempty->FindNode(22), null);
    EXPECT_NE(nempty->FindNode(11), null);
    EXPECT_NE(nempty->FindNode(19), null);
    EXPECT_EQ(nempty->FindNode(18.999), null);
}

TEST_F(Axioms, axiom1)
{
    vector<BinaryTree::Node_t*> test_color;
    axioms->GetLeafNodes(test_color);
    int len = test_color.size();
    for(int i = 0; i < len; i++)
    {
        EXPECT_EQ(test_color[i]->color, BLACK);
        EXPECT_NE(test_color[i]->color, RED);
    }

}

TEST_F(Axioms, axiom2)
{
    vector<BinaryTree::Node_t*> test_right_left;
    axioms->GetAllNodes(test_right_left);
    int len = test_right_left.size();
    for(int i = 0; i < len; i++)
    {
        if(test_right_left[i]->color == RED)
        {
          EXPECT_EQ(test_right_left[i]->pLeft->color, BLACK);
          EXPECT_NE(test_right_left[i]->pRight->color, RED);
        }
    }
}

TEST_F(Axioms, axiom3)
{
    vector<BinaryTree::Node_t*> test_way;
    axioms->GetLeafNodes(test_way);
    Node_t* parent;
    int len = test_way.size();
    int cnt_1 = 0;
    int cnt_2 = 0;
    parent = test_way[0]->pParent;
    while(parent != NULL)
    {
        if(parent->color == BLACK)
        {
            cnt_2++;
        }
        parent = parent->pParent;
    }
    for(int i = 1; i < len; i++)
    {
        cnt_1 = 0;
        parent = test_way[i]->pParent;
        while(parent != NULL)
        {
            if(parent->color == BLACK)
            {
                cnt_1++;
            }
            parent = parent->pParent;
        }
        EXPECT_EQ(cnt_1,cnt_2);
        cnt_2 = cnt_1;
    }
}

/*** Konec souboru black_box_tests.cpp ***/
