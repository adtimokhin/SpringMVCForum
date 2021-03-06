package com.adtimokhin.utils;

import com.adtimokhin.models.company.Company;
import com.adtimokhin.models.company.Token;
import com.adtimokhin.services.company.CompanyService;
import com.adtimokhin.services.company.TokenService;
import com.adtimokhin.services.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

/**
 * @author adtimokhin
 * 08.06.2021
 **/

@Component
public class TokenGenerator {
    /**
     * Token is a 6-digit unique value used to identify a user as being a member of some supportive organization.
     * First 3 digits represent a unique organization number
     * Last 3 digits represent a unique user number for the specific organization
     **/

    @Autowired
    private CompanyService companyService;

    @Autowired
    private TokenService tokenService;

    @Autowired
    private UserService userService;

    private static Random random = new Random();

    /**
     * This method generates required number of tokens for a given organization.
     **/
    public List<String> generateTokens(Company company, int quantity) {

        if (quantity <= 0 || company == null) {
            return null;
        }

        // Getting all companies that have tokens. Those are ones that have been verified.
        List<Company> companies = companyService.getAllVerifiedCompanies();
        if (companies.contains(company)) {
            // Getting the company number.
            String orgNum = tokenService.getSampleToken(company).getOrganizationNumber();
            String[] userNumbers = new String[quantity];
            // Getting already occupied user names for that organization.
            ArrayList<String> occupiedUserNumbers = (ArrayList<String>) tokenService.getAllTokens(company).stream().map(Token::getUserNumber)
                    .collect(Collectors.toList());
            for (int i = 0; i < userNumbers.length; i++) {
                String num = generateRandomNumber();
                while (isNotUnique(num, occupiedUserNumbers)) {
                    num = generateRandomNumber();
                }
                userNumbers[i] = num;
                occupiedUserNumbers.add(num);
            }

            return assembleTokens(orgNum, userNumbers);
        }

        // Getting all occupied organization numbers.
        String[] occupiedOrgNums = new String[companies.size()];
        for (int i = 0; i < occupiedOrgNums.length; i++) {
            Token token = tokenService.getSampleToken(companies.get(i));
            occupiedOrgNums[i] = token.getOrganizationNumber();
        }
        // Generating unique organization number.
        String orgNum;
        if (occupiedOrgNums.length != 0) {
            orgNum = occupiedOrgNums[0];
            while (isNotUnique(orgNum, occupiedOrgNums)) {
                orgNum = generateRandomNumber();
            }
        } else {
            orgNum = generateRandomNumber();
        }
        // Generating required number of user numbers.
        String[] userNumbers = new String[quantity];
        for (int i = 0; i < userNumbers.length; i++) {
            String num = generateRandomNumber();
            while (isNotUnique(num, userNumbers)) {
                num = generateRandomNumber();
            }
            userNumbers[i] = num;
        }
        // Assembling tokens.
        return assembleTokens(orgNum, userNumbers);
    }

    private String generateRandomNumber() {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < 3; i++) {
            stringBuilder.append(random.nextInt(10));
        }
        return String.valueOf(stringBuilder);
    }

    private boolean isNotUnique(String num, String[] nums) {
        for (String n :
                nums) {
            if (n == null) {
                return false;
            }
            if (n.equals(num)) {
                return true;
            }
        }
        return false;
    }

    private boolean isNotUnique(String num, ArrayList<String> nums) {
        for (String n :
                nums) {
            if (n.equals(num)) {
                return true;
            }
        }
        return false;
    }

    private ArrayList<String> assembleTokens(String orgNum, String[] userNumbers) {
        ArrayList<String> tokens = new ArrayList<>();
        for (String userNum :
                userNumbers) {
            tokens.add(orgNum + userNum);
        }
        return tokens;
    }


    //email verification token generation

    public String generateUniqueToken(int length, boolean isEmailVerificationToken) {
        StringBuilder newToken = new StringBuilder();
        List<String> tokens;
        if (isEmailVerificationToken){
         tokens = userService.getAllEmailVerificationTokens();
        }else {
            tokens = userService.getAllPasswordResetTokens();
        }
        for (int i = 0; i < length; i++) {
            //generating a random symbol for a new token.
            char symbol = generateSymbol();
            if (tokens.size() != 0) {
                List<String> subTokens = new ArrayList<>();
                for (int j = 0; j < tokens.size(); j++) {
                    String token = tokens.get(j);
                    if (token.toCharArray()[i] == symbol) {
                        subTokens.add(token);
                    }
                }
                tokens = subTokens;
            }
            newToken.append(symbol);
        }
        if (tokens.size() != 0) { //That means that token is not unique. Hence, we should do the token again.
            return generateUniqueToken(length, isEmailVerificationToken);
        }
        return newToken.toString();
    }


    private char generateSymbol() {

        int i = random.nextInt(3);
        int j;

        if (i == 0){
            j = 57 - random.nextInt(10);
        }else if (i == 1){
            j = 90 - random.nextInt(26);
        }else {
            j = 122 - random.nextInt(26);
        }

        return Character.toString(j).toCharArray()[0];
    }

}
