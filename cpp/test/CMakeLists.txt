#include_directories(${PROJECT_SOURCE_DIR}/dataStructure)
set(CMAKE_CXX_STAND 17) #cpp 17
set(EXECUTABLE_OUTPUT_PATH ${PROJECT_SOURCE_DIR}/bin)
add_executable(Main main.cpp)
