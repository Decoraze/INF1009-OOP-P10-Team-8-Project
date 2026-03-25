package com.p10.game.ui;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/*
 * PhishingEmailPool is a static factory that creates and stores
 * all preset phishing email templates. Each email has different
 * suspicious elements for gameplay variety.
 *
 * Uses Factory Pattern — centralises email creation.
 * OCP compliant — add new emails by adding methods, no logic changes.
 */
public class PhishingEmailPool {

    // all available phishing emails
    private List<PhishingEmail> emails;
    // random selector for picking emails
    private Random random;

    /*
     * Builds the email pool on creation.
     * screenW and screenH needed to calculate zone positions
     * relative to the email panel on screen.
     */
    public PhishingEmailPool(float screenW, float screenH) {
        this.emails = new ArrayList<>();
        this.random = new Random();
        buildPool(screenW, screenH);
    }

    /*
     * Get a random email from the pool.
     * Called by PhishingPopup when player dies on phishing level.
     */
    public PhishingEmail getRandomEmail() {
        return emails.get(random.nextInt(emails.size()));
    }

    /*
     * Build all preset phishing emails.
     * Panel is centered on screen: x = screenW/2 - 300, y = screenH/2 - 200
     * Panel size: 600 x 400
     * Zone coordinates are absolute screen positions.
     */
    private void buildPool(float screenW, float screenH) {
        float px = screenW / 2 - 250;
        float py = 10;
        float ph = screenH - 20;
        // offset zones DOWN by zone height (22px) because:
        // font.draw Y = top of text (draws downward)
        // ShapeRenderer rect Y = bottom of rect (draws upward)
        float zoneH = 22f;
        float senderY = py + ph - 90 - zoneH;
        float bodyStartY = py + ph - 150 - zoneH;
        float lineHeight = 16f;

        // helper: get Y position for a specific body line number (0-indexed)
        // line 0 = first line of body, line 1 = second, etc.

        // --- EMAIL 1: Fake bank ---
        PhishingEmail e1 = new PhishingEmail(
                "security@bank0famerica.com",
                "URGENT: Your Account Has Been Compromised!",
                new String[] {
                        "Dear Valued Customer,", // line 0
                        "", // line 1
                        "We detected unusual activity.", // line 2
                        "Click below to verify immediately:", // line 3
                        "", // line 4
                        ">> http://bank0famerica-secure.ru/verify <<", // line 5
                        "", // line 6
                        "If you do not respond in 24 hours,", // line 7
                        "your account will be locked.", // line 8
                        "", // line 9
                        "Best regards,", // line 10
                        "Bank of America Security Team" // line 11
                });
        e1.addZone(px + 60, senderY, 300, 22, "Fake sender: '0' instead of 'O'");
        e1.addZone(px + 20, bodyStartY - (5 * lineHeight), 400, 22, "Fake URL: .ru domain");
        e1.addZone(px + 20, bodyStartY - (8 * lineHeight), 350, 22, "Urgency: threatening lock");
        emails.add(e1);

        // --- EMAIL 2: Fake IT support ---
        PhishingEmail e2 = new PhishingEmail(
                "admin@g00gle-support.net",
                "Password Reset Required - Action Needed",
                new String[] {
                        "Hi User,", // line 0
                        "", // line 1
                        "Your Google password expires today.", // line 2
                        "Reset it immediately:", // line 3
                        "", // line 4
                        ">> http://g00gle-support.net/reset <<", // line 5
                        "", // line 6
                        "Failure to reset = account deletion.", // line 7
                        "", // line 8
                        "Thank you,", // line 9
                        "Google IT Support" // line 10
                });
        e2.addZone(px + 60, senderY, 300, 22, "Fake sender: '00' instead of 'oo'");
        e2.addZone(px + 20, bodyStartY - (5 * lineHeight), 380, 22, "Fake URL: not real Google");
        e2.addZone(px + 20, bodyStartY - (7 * lineHeight), 400, 22, "Scare tactic: deletion threat");
        emails.add(e2);

        // --- EMAIL 3: Fake delivery ---
        PhishingEmail e3 = new PhishingEmail(
                "noreply@fed-ex-delivery.xyz",
                "Your Package Could Not Be Delivered",
                new String[] {
                        "Dear Customer,", // line 0
                        "", // line 1
                        "We attempted to deliver your package", // line 2
                        "but were unable to reach you.", // line 3
                        "", // line 4
                        "Confirm address and pay redelivery", // line 5
                        "fee of $1.99:", // line 6
                        "", // line 7
                        ">> http://fedex-redeliver.xyz/pay <<", // line 8
                        "", // line 9
                        "Sincerely,", // line 10
                        "FedEx Delivery Team" // line 11
                });
        e3.addZone(px + 60, senderY, 300, 22, "Fake sender: .xyz domain");
        e3.addZone(px + 20, bodyStartY - (8 * lineHeight), 370, 22, "Fake URL: .xyz not real FedEx");
        e3.addZone(px + 20, bodyStartY - (6 * lineHeight), 300, 22, "Bait: unexpected fee");
        emails.add(e3);

        // --- EMAIL 4: Fake prize ---
        PhishingEmail e4 = new PhishingEmail(
                "winner-notify@amaz0n-rewards.tk",
                "You Won a $500 Gift Card!",
                new String[] {
                        "Dear Lucky Winner!", // line 0
                        "", // line 1
                        "You have been selected to receive", // line 2
                        "a $500 Amazon Gift Card!", // line 3
                        "", // line 4
                        "Claim your prize now:", // line 5
                        ">> http://amaz0n-rewards.tk/claim <<", // line 6
                        "", // line 7
                        "Offer expires in 1 hour!", // line 8
                        "", // line 9
                        "Amazon Rewards Team" // line 10
                });
        e4.addZone(px + 60, senderY, 330, 22, "Fake sender: '0' and .tk domain");
        e4.addZone(px + 20, bodyStartY - (6 * lineHeight), 380, 22, "Fake URL: .tk not real Amazon");
        e4.addZone(px + 20, bodyStartY - (8 * lineHeight), 300, 22, "Too good to be true: free gift");
        emails.add(e4);

        // --- EMAIL 5: Fake CEO ---
        PhishingEmail e5 = new PhishingEmail(
                "ceo@company-internal.org",
                "Urgent Wire Transfer Needed",
                new String[] {
                        "Hi,", // line 0
                        "", // line 1
                        "Process an urgent wire transfer", // line 2
                        "of $25,000 to this account:", // line 3
                        "", // line 4
                        "Account: 4839-2948-1028", // line 5
                        "Bank: Offshore Holdings Ltd", // line 6
                        "", // line 7
                        "Do not discuss this with anyone.", // line 8
                        "This is confidential.", // line 9
                        "", // line 10
                        "Thanks, CEO" // line 11
                });
        e5.addZone(px + 60, senderY, 280, 22, "Fake sender: .org not company email");
        e5.addZone(px + 20, bodyStartY - (8 * lineHeight), 320, 22, "Secrecy: 'do not discuss'");
        e5.addZone(px + 20, bodyStartY - (6 * lineHeight), 300, 22, "Suspicious: offshore account");
        emails.add(e5);
    }
    // all these can be further exppanded upon if needed. but due to time
    // constraints we leave it as 5.

}