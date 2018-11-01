#source test1.sh
export TEMP='test.sh'
eval "${TEST1SCRIPT}"
eval "${TEST2SCRIPT}"
eval "${TEST3SCRIPT}"
echo "From test.sh. The value of TEMP is ${TEMP}"
echo "${FILE1} ${FILE2} - ${NAME1}"
