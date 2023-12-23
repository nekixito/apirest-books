package com.company.books.backend.services;

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

import com.company.books.backend.model.Categoria;
import com.company.books.backend.model.dao.ICategoriaDao;
import com.company.books.backend.response.CategoriaResponseRest;

@Service
public class CategoriaServiceImpl implements ICategoriaService {
	
	private static final Logger log = LoggerFactory.getLogger(CategoriaServiceImpl.class);
	
	@Autowired
	private ICategoriaDao categoriaDao;

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarCategorias() {
		
		log.info("Inicio de metodo buscarCategorias()");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		
		try {
			List<Categoria> categoria = (List<Categoria>) categoriaDao.findAll();
			
			response.getCategoriaResponse().setCategoria(categoria);
			
			response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
			
		} catch (Exception e) {
			response.setMetadata("Respuesta NO ok", "-1", "Error al consultar categorias");
			log.error("Error al consultar categorias: ", e.getMessage());
			e.getStackTrace();
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);//Devuelve 200
	}

	@Override
	@Transactional(readOnly = true)
	public ResponseEntity<CategoriaResponseRest> buscarPorId(Long id) {
		
		log.info("Inicio de metodo buscarPorId(Long id)");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		
		try {
			Optional<Categoria> categoria = categoriaDao.findById(id);
			
			if (categoria.isPresent()) {
				list.add(categoria.get());
				response.getCategoriaResponse().setCategoria(list);
				
				
			}else {
				log.error("Error en consultar categoria: ");
				response.setMetadata("Respuesta NO ok", "-1", "Categoria no encontrada");
				return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NOT_FOUND);
			}
			
			
		} catch (Exception e) {
			
			log.error("Error en consultar categoria ");
			response.setMetadata("Respuesta NO ok", "-1", "Error al consultar categoria");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata("Respuesta ok", "00", "Respuesta exitosa");
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);//Devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> crear(Categoria categoria) {
		
		log.info("Inicio de metodo para crear categorria");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {
			Categoria categoriaGuardada = categoriaDao.save(categoria);
			
			if(categoriaGuardada != null) {
				list.add(categoriaGuardada);
				response.getCategoriaResponse().setCategoria(list);
			}else {
				log.error("Error en crear categoria: ");
				response.setMetadata("Respuesta NO ok", "-1", "Categoria no creada");
				return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.BAD_REQUEST);
			}
		}catch(Exception e) {
			log.error("Error al crear categoria ");
			response.setMetadata("Respuesta NO ok", "-1", "Error al crear categoria");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		response.setMetadata("Respuesta ok", "00", "Categoria creada");
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);//Devuelve 200

	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> actualizar(Categoria categoria, Long id) {
		
		log.info("Inicio de metodo actualizar");
		
		CategoriaResponseRest response = new CategoriaResponseRest();
		List<Categoria> list = new ArrayList<>();
		
		try {
			Optional<Categoria> categoriaBuscada = categoriaDao.findById(id);
			
			if (categoriaBuscada.isPresent()) {
				categoriaBuscada.get().setNombre(categoria.getNombre());
				categoriaBuscada.get().setDescripcion(categoria.getDescripcion());
				
				Categoria categoriaActualizada = categoriaDao.save(categoriaBuscada.get()); //actualizando
				
				if (categoriaActualizada != null) {
					response.setMetadata("Respuesta ok", "00", "Categoria actualizada");
					list.add(categoriaActualizada);
					response.getCategoriaResponse().setCategoria(list);
				} else {
					log.error("Error en actualizar categoria");
					response.setMetadata("Respuesta nok", "-1", "Categoria NO actualizada");
					return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.BAD_REQUEST);
				}
			} else {
				log.error("Error en actualizar categoria");
				response.setMetadata("Respuesta nok", "-1", "Categoria NO actualizada");
				return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.NOT_FOUND);

			}
		}catch(Exception e) {
			log.error("Error al actualizar categoria: ",e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Categoria NO actualizada");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);//Devuelve 200
	}

	@Override
	@Transactional
	public ResponseEntity<CategoriaResponseRest> eliminar(Long id) {
		log.info("Inicio metodo eliminar");
		
		CategoriaResponseRest response = new  CategoriaResponseRest();
		
		try {
			//Eliminamos el registro
			categoriaDao.deleteById(id);
			response.setMetadata("Respuesta ok", "00", "Categoria eliminada");
			
		} catch (Exception e) {
			log.error("Error al eliminar categoria: ",e.getMessage());
			e.getStackTrace();
			response.setMetadata("Respuesta nok", "-1", "Categoria no eliminada");
			return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<CategoriaResponseRest>(response,HttpStatus.OK);//Devuelve 200
	}
	
	

}
