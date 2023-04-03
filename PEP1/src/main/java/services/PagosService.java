package services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.PagosRepository;

@Service
public class PagosService {

    @Autowired
    PagosRepository pagosRepository;

}
