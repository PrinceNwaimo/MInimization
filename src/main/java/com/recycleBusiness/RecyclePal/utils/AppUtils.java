package com.recycleBusiness.RecyclePal.utils;

public class AppUtils {
    public static final String USERNAME_OR_PASSWORD_NOT_VALID = "It appears that the email %s or username %s you provided is already taken by another user";
    public static final String EMAIL_Taken_ERROR_MSG ="It appears that the email %s you provided is already taken by another user";
    public static final String USER_NOT_SAVE_iNTO_DB= "It appears that this %s UserName not save";
    public static final String USERNAME_NOT_FOUND = "It appears that this %s UserName not found";
    public static final String USER_NAME_DOES_NOT_EXIST = "The username %s does not exist.";
    public static final String ACTIVATE_ACCOUNT_URL = "localhost:8080/api/v1/customer/verify %s" ;
    public static final String API_KEY_VALUE = "api-key";
    public static final String EMAIL_URL="https://api.brevo.com/v3/smtp/email";
    public static final String SENDER = "sender";
    public static final String TO = "to";
    public static final String SUBJECT="subject";
    public static final String COMPANY_NAME = "RecyclePal";


    public  static String buildWelcomeEmail(String userName, String companyName, String platformName) {
        String emailTemplate = "<!DOCTYPE html>\n" +
                "<html>\n" +
                "<head>\n" +
                "    <title>Welcome to " + companyName + "!</title>\n" +
                "    <style>\n" +
                "        body {\n" +
                "            font-family: Arial, sans-serif;\n" +
                "            line-height: 1.6;\n" +
                "            margin: 0;\n" +
                "            padding: 0;\n" +
                "        }\n" +
                "\n" +
                "        .container {\n" +
                "            max-width: 600px;\n" +
                "            margin: 0 auto;\n" +
                "            padding: 20px;\n" +
                "            background-color: #f2f2f2;\n" +
                "        }\n" +
                "\n" +
                "        h1 {\n" +
                "            color: #0066cc;\n" +
                "        }\n" +
                "\n" +
                "        p {\n" +
                "            margin-bottom: 20px;\n" +
                "        }\n" +
                "\n" +
                "        ul {\n" +
                "            margin-bottom: 20px;\n" +
                "            list-style-type: disc;\n" +
                "            padding-left: 20px;\n" +
                "        }\n" +
                "\n" +
                "        li {\n" +
                "            margin-bottom: 10px;\n" +
                "        }\n" +
                "\n" +
                "        .button {\n" +
                "            display: inline-block;\n" +
                "            background-color: #0066cc;\n" +
                "            color: #fff;\n" +
                "            padding: 10px 20px;\n" +
                "            text-decoration: none;\n" +
                "            border-radius: 5px;\n" +
                "        }\n" +
                "\n" +
                "        .button:hover {\n" +
                "            background-color: #0052a3;\n" +
                "        }\n" +
                "    </style>\n" +
                "</head>\n" +
                "<body>\n" +
                "    <div class=\"container\">\n" +
                "        <h1>Welcome to " + companyName + "!</h1>\n" +
                "        <p>Dear " + userName + ",</p>\n" +
                "        <p>Welcome to " + companyName + "! We are thrilled to have you as a part of our community.</p>\n" +
                "        <p>Thank you for joining us and taking the first step towards making a positive impact on the environment. Our mission is to promote sustainable practices and foster a greener future, and your presence is vital in achieving that goal.</p>\n" +
                "        <p>With " + platformName + ", you can actively participate in plastic waste collection and recycling efforts in your community. Together, we can make a significant difference in reducing plastic pollution and creating a cleaner, healthier planet.</p>\n" +
                "        <p>Here are a few things you can do on our platform:</p>\n" +
                "        <ul>\n" +
                "            <li>Submit Waste Collection Requests: Easily request waste collection services for recycling plastic waste from your location.</li>\n" +
                "            <li>Track Collection Status: Stay updated on the status of your waste collection requests in real-time.</li>\n" +
                "            <li>Explore Recycling Centers: Discover nearby recycling centers where you can drop off recyclable plastic materials.</li>\n" +
                "            <li>Make Payments: Conveniently make payments for waste collection services through our secure payment system.</li>\n" +
                "            <li>Stay Informed: Receive important notifications and updates about the latest recycling initiatives and environmental news.</li>\n" +
                "        </ul>\n" +
                "        <p>We are committed to providing you with a seamless and enjoyable experience on our platform. Should you have any questions, feedback, or concerns, please don't hesitate to reach out to our customer support team. Your satisfaction is our top priority.</p>\n" +
                "  " +
                "      <p>Once again, welcome aboard! Your dedication to environmental conservation and sustainable practices is highly valued, and we look forward to embarking on this journey together.</p>\n";
        return emailTemplate;
    }

    }

