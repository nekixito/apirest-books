package com.company.books.backend.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.company.books.backend.model.Libro;
import com.company.books.backend.model.dao.ILibroDao;
import com.company.books.backend.response.LibroResponseRest;

@Service
public class LibroServiceImpl implements ILibroService {
	
	private static final Logger log = LoggerFactory.getLogger(LibroServiceImpl.class);
	
	@Autowired
	private ILibroDao libroDao;
	
	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarLibros() {
		log.info("Inicio de método buscarLibros()");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			List<Libro> libro = (List<Libro>) libroDao.findAll();
			
			response.getLibroResponse().setLibro(libro);
			response.setMetadata("Respuesta OK", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOT OK","-1","Respuesta incorrecta");
			log.error("Error al consultar libros: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); //Devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<LibroResponseRest> buscarPorId(Long id) {
		log.info("Inicio de método buscarPorId()");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> listaLibros = new ArrayList<>();
		
		try {
			
			Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if (libroBuscado.isPresent()) {
				listaLibros.add(libroBuscado.get());
				response.getLibroResponse().setLibro(listaLibros);
			}else {
				log.error("Error al consultar libro");
				response.setMetadata("Respuesta NOT OK","-1","Libro no encontrado");
				return new ResponseEntity<LibroResponseRest>(response,HttpStatus.NOT_FOUND);
			}
		} catch (Exception e) {
			response.setMetadata("Respuesta NOT OK","-1","Respuesta incorrecta");
			log.error("Error al consultar libro: ", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<LibroResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata("Respuesta OK","00","Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response,HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> crear(Libro libro) {
		log.info("Inicio de método crear()");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			Libro libroGuardado = libroDao.save(libro);
			
			if(libroGuardado != null) {
				
				list.add(libroGuardado);
				response.getLibroResponse().setLibro(list);
				
			}else {
				
				log.error("Error al guardar libro");
				response.setMetadata("Respuesta NOT OK","-1","Libro no guardado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST); // 
			}
			
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOT OK","-1","Respuesta incorrecta");
			log.error("Error al crear libro: ", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<LibroResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata("Respuesta OK","00","Respuesta exitosa");
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> actualizar(Libro libro, Long id) {
		log.info("Inicio de método actualizar()");
		
		LibroResponseRest response = new LibroResponseRest();
		List<Libro> list = new ArrayList<>();
		
		try {
			 Optional<Libro> libroBuscado = libroDao.findById(id);
			
			if(libroBuscado.isPresent()) {
				
				libroBuscado.get().setNombre(libro.getNombre());
				libroBuscado.get().setDescripcion(libro.getDescripcion());
				libroBuscado.get().setCategoria(libro.getCategoria());
				
				Libro libroActualizado = libroDao.save(libroBuscado.get());
				
				if(libroActualizado != null) {
					response.setMetadata("Respuesta OK","00","Libro actualizado");
					list.add(libroActualizado);
					response.getLibroResponse().setLibro(list);
				}else {
					log.error("Error en grabar libro");
					response.setMetadata("Respuesta nok","-1","Libro no actualizado");
					return new ResponseEntity<LibroResponseRest>(response, HttpStatus.BAD_REQUEST); //
				}

			}else {
				
				log.error("Error al actualizar libro");
				response.setMetadata("Respuesta NOT OK","-1","Libro no actualizado");
				return new ResponseEntity<LibroResponseRest>(response, HttpStatus.NOT_FOUND); // 
			}
			
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOT OK","-1","Respuesta incorrecta");
			log.error("Error al actualizar libro: ", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK);
	}

	@Override
	@Transactional
	public ResponseEntity<LibroResponseRest> eliminar(Long id) {
		log.info("Inicio de metodo eliminarPorId()");
		
		LibroResponseRest response = new LibroResponseRest();
		
		try {
			libroDao.deleteById(id);
			response.setMetadata("Respuesta OK","00","Libro eliminado");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NOT OK","-1","Libro no eliminado");
			log.error("Error al eliminar libro: ", e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<LibroResponseRest>(response, HttpStatus.INTERNAL_SERVER_ERROR); //
		}
		
		return new ResponseEntity<LibroResponseRest>(response, HttpStatus.OK); 
	}

}
