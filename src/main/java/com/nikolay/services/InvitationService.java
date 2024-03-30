package com.nikolay.services;

import com.nikolay.dto.CheckInvitationDTO;
import com.nikolay.dto.CreateInvitationDTO;
import com.nikolay.exceptions.RoomNotFoundException;
import com.nikolay.exceptions.UserNotFoundException;
import com.nikolay.exceptions.UserNotInvitedException;
import com.nikolay.models.Invitation;
import com.nikolay.models.Room;
import com.nikolay.models.UserModel;
import com.nikolay.repositories.InvitationRepository;
import com.nikolay.repositories.RoomRepository;
import com.nikolay.repositories.UserModelRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional(readOnly = true)
public class InvitationService {

    private final InvitationRepository invitationRepository;
    private final RoomRepository roomRepository;
    private final UserModelRepository userModelRepository;

    @Autowired
    public InvitationService(InvitationRepository invitationRepository, RoomRepository roomRepository, UserModelRepository userModelRepository) {
        this.invitationRepository = invitationRepository;
        this.roomRepository = roomRepository;
        this.userModelRepository = userModelRepository;
    }

    @Transactional
    public void createInvitation(CreateInvitationDTO createInvitationDTO) {
        // no room found
        Room room = roomRepository.findByName(createInvitationDTO.getRoom()).orElse(null);
        if (room == null) throw new RoomNotFoundException();

        // no user found
        List<UserModel> userModels = userModelRepository.findAllByEmailIn(createInvitationDTO.getEmail());
        if (userModels.isEmpty()) throw new UserNotFoundException();

        for (UserModel userModel : userModels) {
            Invitation invitation = new Invitation();
            room.addInvitation(invitation);
            userModel.addInvitation(invitation);

            invitationRepository.save(invitation);
        }
    }

    public void checkInvitation(CheckInvitationDTO checkInvitationDTO) {
        // check if it is creator
        Room room = roomRepository.findByNameAndUserModelEmail(checkInvitationDTO.getRoom(), checkInvitationDTO.getEmail()).orElse(null);

        // check if the user is invited
        Invitation invitation = invitationRepository.findByUserModelEmailAndRoomName(checkInvitationDTO.getEmail(), checkInvitationDTO.getRoom()).orElse(null);

        if (room == null && invitation == null) throw new UserNotInvitedException();
    }

}
