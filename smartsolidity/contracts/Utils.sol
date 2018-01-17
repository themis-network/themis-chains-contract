pragma solidity ^0.4.18;


library Utils {
    /**
     * @dev copy from https://ethereum.stackexchange.com/questions/9142/how-to-convert-a-string-to-bytes32
     */
    function stringToBytes32(string memory source) internal pure returns (bytes32 result) {
        bytes memory tempEmptyStringTest = bytes(source);
        if (tempEmptyStringTest.length == 0) {
            return 0x0;
        }

        assembly {
            result := mload(add(source, 32))
        }
    }

    /**
     * @dev copy from https://ethereum.stackexchange.com/questions/2519/how-to-convert-a-bytes32-to-string
     */
    function bytes32ToString(bytes32 x) internal pure returns (string) {
        bytes memory bytesString = new bytes(32);
        uint charCount = 0;
        for (uint j = 0; j < 32; j++) {
            byte char = byte(bytes32(uint(x) * 2 ** (8 * j)));
            if (char != 0) {
                bytesString[charCount] = char;
                charCount++;
            }
        }
        bytes memory bytesStringTrimmed = new bytes(charCount);
        for (j = 0; j < charCount; j++) {
            bytesStringTrimmed[j] = bytesString[j];
        }
        return string(bytesStringTrimmed);
    }


    function mul(uint a, uint b) internal pure returns (uint) {
        uint256 c = a * b;
        assert(a == 0 || c / a == b);
        return c;
    }

    function div(uint a, uint b) internal pure returns (uint) {
        // assert(b > 0); // Solidity automatically throws when dividing by 0
        uint c = a / b;
        // assert(a == b * c + a % b); // There is no case in which this doesn't hold
        return c;
    }

    function sub(uint a, uint b) internal pure returns (uint) {
        assert(b <= a);
        return a - b;
    }

    function add(uint a, uint b) internal pure returns (uint) {
        uint c = a + b;
        assert(c >= a);
        return c;
    }

    function intAdd(int a, int b) internal pure returns (int) {
        int c = a + b;
        return c;
    }

    function intSub(int a, int b) internal pure returns (int) {
        int c = a - b;
        return c;
    }

    function strConcatThree(string a, string b, string c) internal view returns(string) {
        string memory tmpRes = strConcat(a, b);
        return strConcat(tmpRes, c);
    }

    function strConcat(string a, string b) internal view returns(string) {
        bytes memory bytesA = bytes(a);
        bytes memory bytesB = bytes(b);

        string memory res = new string(bytesA.length + bytesB.length);
        bytes memory resBytes = bytes(res);

        uint index = 0;
        uint i = 0;

        for (i = 0; i < bytesA.length; i++) {
            resBytes[index++] =  bytesA[i];
        }

        for (i = 0; i < bytesB.length; i++) {
            resBytes[index++] = bytesB[i];
        }

        return string(resBytes);
    }


}