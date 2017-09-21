package com.project.hr.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.application.FacesMessage;

import javax.faces.context.FacesContext;
import javax.inject.Named;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

@Named
@RequestScoped
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
@EqualsAndHashCode
public class MailboxView implements Serializable{
    
    private TreeNode mailboxes;

    private List<Mail> mails;

    private Mail mail;

    private TreeNode mailbox;

    @PostConstruct
    public void init() {
        mailboxes = new DefaultTreeNode("root", null);

        TreeNode inbox = new DefaultTreeNode("i","Inbox", mailboxes);
		TreeNode sent = new DefaultTreeNode("s", "Sent", mailboxes);
		TreeNode trash = new DefaultTreeNode("t", "Trash", mailboxes);
        TreeNode junk = new DefaultTreeNode("j", "Junk", mailboxes);

        TreeNode gmail = new DefaultTreeNode("Gmail", inbox);
        TreeNode hotmail = new DefaultTreeNode("Hotmail", inbox);

        mails = new ArrayList<>();
        mails.add(new Mail("optimus@primefaces.com", "Team Meeting", "Meeting to discuss roadmap", new Date()));
        mails.add(new Mail("spammer@spammer.com", "You've won Lottery", "Send me your credit card info to claim", new Date()));
        mails.add(new Mail("spammer@spammer.com", "Your email has won", "Click the exe file to claim", new Date()));
        mails.add(new Mail("primefaces-commits", "[primefaces] r4491 - Layout mailbox sample", "Revision:4490 Author:cagatay.civici" ,new Date()));
    }

   

    public void send() {
        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Mail Sent!"));
    }
}
