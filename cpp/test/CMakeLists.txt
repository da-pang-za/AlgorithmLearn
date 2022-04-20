#include_directories(${PROJECT_SOURCE_DIR}/dataStructure)
set(CMAKE_CXX_STAND 17)
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/bin)
#添加头文件搜索路径  这样就不用在include的地方写一堆"../"了
INCLUDE_DIRECTORIES(${PROJECT_SOURCE_DIR})
INCLUDE_DIRECTORIES(${PROJECT_SOURCE_DIR}/usage)
add_executable(main main.cpp)
add_executable(ACM_MODE ACM_MODE.cpp)
set(CMAKE_CXX_FLAGS "${CMAKE_CXX_FLAGS} -std=c++17")
