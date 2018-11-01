#source test1.sh
TEMP='test.sh'
"${TEST1SCRIPT}"
"${TEST2SCRIPT}"
"${TEST3SCRIPT}"
echo "From test.sh. The value of TEMP is ${TEMP}"
echo "${FILE1} ${FILE2} - ${NAME1}"
