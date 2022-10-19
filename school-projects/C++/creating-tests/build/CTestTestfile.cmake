# CMake generated Testfile for 
# Source directory: /mnt/c/C/2.semester/ivs_project_1_2020/assignment
# Build directory: /mnt/c/C/2.semester/ivs_project_1_2020/assignment/build
# 
# This file includes the relevant testing commands required for 
# testing this directory and lists subdirectories to be tested as well.
add_test(Empty.insertN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Empty.insertN")
add_test(Empty.deleteN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Empty.deleteN")
add_test(Empty.findN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Empty.findN")
add_test(Nempty.insertN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Nempty.insertN")
add_test(Nempty.deleteN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Nempty.deleteN")
add_test(Nempty.findN "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Nempty.findN")
add_test(Axioms.axiom1 "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Axioms.axiom1")
add_test(Axioms.axiom2 "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Axioms.axiom2")
add_test(Axioms.axiom3 "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/black_box_test" "--gtest_filter=Axioms.axiom3")
add_test(TMatrix.Constructor "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.Constructor")
add_test(TMatrix.Destruktor "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.Destruktor")
add_test(TMatrix.set "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.set")
add_test(TMatrix.get "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.get")
add_test(TMatrix.equal "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.equal")
add_test(TMatrix.plus "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.plus")
add_test(TMatrix.multiply "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.multiply")
add_test(TMatrix.solve "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/white_box_test" "--gtest_filter=TMatrix.solve")
add_test(NonEmptyQueue.Insert "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=NonEmptyQueue.Insert")
add_test(NonEmptyQueue.RemoveAllForward "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=NonEmptyQueue.RemoveAllForward")
add_test(NonEmptyQueue.RemoveAllBackward "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=NonEmptyQueue.RemoveAllBackward")
add_test(NonEmptyQueue.Find "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=NonEmptyQueue.Find")
add_test(EmptyQueue.Insert "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=EmptyQueue.Insert")
add_test(EmptyQueue.Remove "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=EmptyQueue.Remove")
add_test(EmptyQueue.Find "/mnt/c/C/2.semester/ivs_project_1_2020/assignment/build/tdd_test" "--gtest_filter=EmptyQueue.Find")
subdirs("googletest-build")
