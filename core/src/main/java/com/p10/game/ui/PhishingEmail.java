package com.p10.game.ui;

import java.util.ArrayList;
import java.util.List;

import com.badlogic.gdx.math.Rectangle;

/* PhishingEmail represents a single phishing email template.
 * Contains the email display content (sender, subject, body)
 * and a list of suspicious zones the player must identify.
 * 
 * This is a pure data class — no rendering, no input handling.
 * Used by PhishingPopup to display and PhishingEmailPool to create. 
 * SO ITS SRP REQ as its just to store data. 
 */
public class PhishingEmail {

    // email header info
    private String sender; // fake sender address (contains suspicious elements)
    private String subject; // email subject line
    private String[] bodyLines; // body text split into lines for rendering

    // list of clickable suspicious zones on this email
    private List<SuspiciousZone> zones;

    /*
     * Inner class representing one suspicious clickable area on the email.
     * Each zone has a bounding rectangle and a label explaining why it's
     * suspicious.
     */
    public static class SuspiciousZone {
        private Rectangle bounds; // clickable area on screen
        private String label; // explanation shown after player clicks it

        public SuspiciousZone(float x, float y, float w, float h, String label) {
            this.bounds = new Rectangle(x, y, w, h);
            this.label = label;
        }

        // getters for popup to read zone data
        public Rectangle getBounds() {
            return bounds;
        }

        public String getLabel() {
            return label;
        }
    }

    public PhishingEmail(String sender, String subject, String[] bodyLines) {
        this.sender = sender;
        this.subject = subject;
        this.bodyLines = bodyLines;
        this.zones = new ArrayList<>();
    }

    /*
     * Add a suspicious zone to this email.
     * Called by PhishingEmailPool when building email templates.
     */
    public void addZone(float x, float y, float w, float h, String label) {
        zones.add(new SuspiciousZone(x, y, w, h, label));
    }

    // --- Getters ---
    public String getSender() {
        return sender;
    }

    public String getSubject() {
        return subject;
    }

    public String[] getBodyLines() {
        return bodyLines;
    }

    public List<SuspiciousZone> getZones() {
        return zones;
    }

    public int getZoneCount() {
        return zones.size();
    }
}