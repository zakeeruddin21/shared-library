#source test1.sh
TEMP='test.sh'
source "${TEST1SCRIPT}"
source "${TEST2SCRIPT}"
source "${TEST3SCRIPT}"
echo "From test.sh. The value of TEMP is ${TEMP}"
echo "${FILE1} ${FILE2} - ${NAME1}"
