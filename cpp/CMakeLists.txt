cmake_minimum_required(VERSION 3.14)
project(LeetCodeCPP)
set(CMAKE_CXX_STAND 17) #cpp 17
#add_subdirectory(${PROJECT_SOURCE_DIR}/dataStructure)
add_subdirectory(${PROJECT_SOURCE_DIR}/test)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17")