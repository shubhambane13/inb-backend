package com.netbank.inb.util;

public class EmailUtil {
    public static String getRegistrationPendingHtml(String name) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <table align="center" width="600" style="background-color: #ffffff; margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #007bff; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; color: #ffffff;">
                                <h2 style="margin: 0;">iNetBank</h2>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 40px; color: #333333;">
                                <h3 style="color: #007bff;">Application Received</h3>
                                <p>Dear <strong>%s</strong>,</p>
                                <p>Thank you for choosing iNetBank. We have received your registration request.</p>
                                <div style="background-color: #e9f7ef; border-left: 5px solid #28a745; padding: 15px; margin: 20px 0;">
                                    <p style="margin: 0; color: #155724;"><strong>Status: Under Review</strong><br>
                                    Your application is currently being reviewed by our compliance team.</p>
                                </div>
                                <p>We will notify you via email as soon as your account is approved. This usually takes 24-48 hours.</p>
                                <p>Best Regards,<br>The iNetBank Team</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #777;">
                                &copy; 2024 iNetBank. All rights reserved.<br>
                                Need help? Contact support@inetbank.com
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(name);
    }

    public static String getAccountApprovedHtml(String name, String accountNumber) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <table align="center" width="600" style="background-color: #ffffff; margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #28a745; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; color: #ffffff;">
                                <h2 style="margin: 0;">Welcome to iNetBank!</h2>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 40px; color: #333333;">
                                <p>Dear <strong>%s</strong>,</p>
                                <p>Great news! Your account application has been approved.</p>
                
                                <p>Your new account is now active and ready to use. Below are your account details:</p>
                
                                <div style="background-color: #f8f9fa; border: 1px solid #ddd; padding: 20px; text-align: center; border-radius: 5px; margin: 20px 0;">
                                    <span style="font-size: 14px; color: #666; display: block; margin-bottom: 5px;">ACCOUNT NUMBER</span>
                                    <strong style="font-size: 24px; color: #333; letter-spacing: 2px;">%s</strong>
                                </div>
                
                                <p>You can now log in to your dashboard to fund your account and start transacting.</p>
                
                                <div style="text-align: center; margin-top: 30px;">
                                    <a href="http://localhost:4200/login" style="background-color: #007bff; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;">Login to Dashboard</a>
                                </div>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #777;">
                                &copy; 2024 iNetBank. All rights reserved.<br>
                                Please do not reply to this automated email.
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(name, accountNumber);
    }

    public static String getAccountRejectedHtml(String name, String reason) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <table align="center" width="600" style="background-color: #ffffff; margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #dc3545; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; color: #ffffff;">
                                <h2 style="margin: 0;">Application Status Update</h2>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 40px; color: #333333;">
                                <p>Dear <strong>%s</strong>,</p>
                                <p>Thank you for your interest in iNetBank.</p>
                
                                <p>After carefully reviewing your application, we regret to inform you that we are unable to approve your account request at this time.</p>
                
                                <div style="background-color: #f8d7da; border-left: 5px solid #dc3545; padding: 15px; margin: 20px 0;">
                                    <p style="margin: 0; color: #721c24;"><strong>Reason:</strong><br>
                                    %s</p>
                                </div>
                
                                <p>If you believe this decision was made in error, or if you have corrected the issue mentioned above, you are welcome to apply again.</p>
                
                                <p>Regards,<br>iNetBank Compliance Team</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #777;">
                                &copy; 2024 iNetBank. All rights reserved.
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(name, reason != null ? reason : "Does not meet eligibility criteria.");
    }

    public static String getAccountLockedHtml(String name) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <table align="center" width="600" style="background-color: #ffffff; margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #ffc107; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; color: #000;">
                                <h2 style="margin: 0;">Security Alert: Account Locked</h2>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 40px; color: #333333;">
                                <p>Dear <strong>%s</strong>,</p>
                                <p>We wanted to inform you that your iNetBank account has been <strong>temporarily locked</strong>.</p>
                
                                <p>This action was taken for the following reason:</p>
                                <ul style="color: #555;">
                                    <li>Administrative Action / Security Concern</li>
                                </ul>
                
                                <p>While your account is locked, you will not be able to log in or perform any transactions.</p>
                
                                <div style="text-align: center; margin-top: 30px;">
                                    <a href="mailto:support@inetbank.com" style="background-color: #343a40; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;">Contact Support</a>
                                </div>
                
                                <p style="margin-top: 30px; font-size: 13px;">If you did not cause this action, please contact us immediately.</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #777;">
                                &copy; 2024 iNetBank. All rights reserved.
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(name);
    }

    public static String getAccountUnlockedHtml(String name) {
        return """
                <html>
                <body style="font-family: Arial, sans-serif; background-color: #f4f4f4; margin: 0; padding: 0;">
                    <table align="center" width="600" style="background-color: #ffffff; margin-top: 20px; border-radius: 8px; box-shadow: 0 4px 8px rgba(0,0,0,0.1);">
                        <tr>
                            <td style="background-color: #17a2b8; padding: 20px; text-align: center; border-radius: 8px 8px 0 0; color: #ffffff;">
                                <h2 style="margin: 0;">Account Restored</h2>
                            </td>
                        </tr>
                        <tr>
                            <td style="padding: 40px; color: #333333;">
                                <p>Dear <strong>%s</strong>,</p>
                                <p>Good news! Your iNetBank account has been successfully <strong>unlocked</strong>.</p>
                
                                <p>You now have full access to your dashboard, funds, and transfer capabilities.</p>
                
                                <div style="text-align: center; margin-top: 30px;">
                                    <a href="http://localhost:4200/login" style="background-color: #17a2b8; color: white; padding: 12px 25px; text-decoration: none; border-radius: 5px; font-weight: bold;">Login Now</a>
                                </div>
                
                                <p style="margin-top: 20px;">If you have trouble logging in, please try resetting your password or contacting support.</p>
                            </td>
                        </tr>
                        <tr>
                            <td style="background-color: #f4f4f4; padding: 20px; text-align: center; font-size: 12px; color: #777;">
                                &copy; 2024 iNetBank. All rights reserved.
                            </td>
                        </tr>
                    </table>
                </body>
                </html>
                """.formatted(name);
    }
}
