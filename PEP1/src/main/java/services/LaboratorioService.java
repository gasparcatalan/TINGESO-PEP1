package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.LaboratorioRepository;

@Service
public class LaboratorioService {

    @Autowired
    LaboratorioRepository laboratorioRepository;


}
