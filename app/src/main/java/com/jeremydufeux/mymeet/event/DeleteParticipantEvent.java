package com.jeremydufeux.mymeet.event;

import com.jeremydufeux.mymeet.model.Participant;

public class DeleteParticipantEvent {

    /**
     * Meeting to delete
     */
    public Participant participant;

    /**
     * Constructor.
     * @param participant the participant to delete
     */
    public DeleteParticipantEvent(Participant participant) {
        this.participant = participant;
    }
}
