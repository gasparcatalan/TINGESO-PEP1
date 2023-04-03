package services;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import repositories.AcopioRepository;

@Service
public class AcopioService {

    @Autowired
    AcopioRepository acopioRepository;
}
