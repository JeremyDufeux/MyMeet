package com.jeremydufeux.mymeet.event;

public class DeleteParticipantEvent {

    /**
     * Meeting to delete
     */
    public String participant;

    /**
     * Constructor.
     * @param participant the participant to delete
     */
    public DeleteParticipantEvent(String participant) {
        this.participant = participant;
    }
}
