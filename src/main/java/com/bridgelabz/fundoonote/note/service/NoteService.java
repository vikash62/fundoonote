package com.bridgelabz.fundoonote.note.service;

import java.lang.reflect.Type;
import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
import org.modelmapper.TypeToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.bridgelabz.fundoonote.note.dto.NoteDto;
import com.bridgelabz.fundoonote.note.exception.NoteException;
import com.bridgelabz.fundoonote.note.model.Note;
import com.bridgelabz.fundoonote.note.repository.NoteRepository;
import com.bridgelabz.fundoonote.note.utility.NoteResponse;
import com.bridgelabz.fundoonote.user.exception.UserException;
import com.bridgelabz.fundoonote.user.model.User;
import com.bridgelabz.fundoonote.user.repository.UserRepository;
import com.bridgelabz.fundoonote.user.utility.TokenManager;


@Service
public class NoteService implements INoteService {
	
	@Autowired
	NoteRepository noteRepository;
	
	@Autowired
	private ModelMapper modelMapper;
	
	@Autowired
	private TokenManager tokenManager;
	
	@Autowired
	private UserRepository userRepository;
	
	@Override
	public NoteResponse addNewNote(NoteDto noteDto, String token) throws NoteException {
		int userId = tokenManager.decodeToken(token);
		User user = userRepository.findById(userId).orElseThrow(()-> new UserException("User not Found"));
		Note note = modelMapper.map(noteDto, Note.class);
//		note.setUser(user);
//		Note newNote =noteRepository.save(note);
		user.getNote().add(note);
		user = userRepository.save(user);
		NoteResponse response = new NoteResponse("The response message: ..Note has been added Sucessfully..", 200, user);
		return response;
	}
	
	@Override
	public NoteResponse deleteNote(int noteId) throws NoteException {
		Optional<Note> findNote = noteRepository.findById(noteId);
		if(!findNote.isPresent()) {
			throw new NoteException(String.format("Note has already Deleted from the Database !!!"));
		}
		noteRepository.deleteById(noteId);
		NoteResponse response = new NoteResponse(
				"The response message : Note has been deleted Sucessfully !!!", 200, noteId);
		return response;
	}
	
	@Override
	public NoteResponse updateNote(int noteId, NoteDto noteDto) {
		Note noteChange = modelMapper.map(noteDto, Note.class);
		noteChange.setNoteId(noteId);
//		Note note = noteRepository.findById(noteId).get();
//		note.setTitle(note.getTitle());
//		note.setDescription(note.getDescription());
		Note noteUpdate = noteRepository.save(noteChange);
		NoteResponse response = new NoteResponse("The response message : Note has been updated Sucessfully",
				200, noteUpdate);
		return response;
	}
	
	@Override
	public List<NoteDto> getAll() {
		List<Note> user = noteRepository.findAll();
		Type userType = new TypeToken<List<NoteDto>>() {

		}.getType();
		List<NoteDto> userDto = modelMapper.map(user, userType);
		return userDto;
	}


}