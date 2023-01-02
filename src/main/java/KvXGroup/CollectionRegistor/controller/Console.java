package KvXGroup.CollectionRegistor.controller;

import KvXGroup.CollectionRegistor.console.ConsoleData;
import KvXGroup.CollectionRegistor.console.ConsoleRepository;
import KvXGroup.CollectionRegistor.console.ConsoleToList;
import KvXGroup.CollectionRegistor.producer.Producer;
import KvXGroup.CollectionRegistor.producer.ProducerRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/console")

public class Console {

    @Autowired
    private ConsoleRepository ConsoleRepo;
    @Autowired
    private ProducerRepository ProducerRepo;

    @GetMapping
    //public Page<ConsoleToList> getConsoles(@PageableDefault(size = 10, sort = {"id"})Pageable pagination){
    public List<ConsoleToList> getConsoles(){
        return ConsoleRepo.getAll();

        //return ConsoleRepo.findAll(pagination).map(ConsoleToList::new);

    }

    @GetMapping("/{id}")
    public KvXGroup.CollectionRegistor.console.Console getConsoleByID(@PathVariable Long id){
        var consoleRaw = ConsoleRepo.findById(id);
        var prodRaw = ProducerRepo.findById(consoleRaw.get().getProducer().getId());

        Producer p = new Producer();
        var prod = p.OptionalToProducer(prodRaw);

        KvXGroup.CollectionRegistor.console.Console c = new KvXGroup.CollectionRegistor.console.Console(consoleRaw, prod);

        return c;
    }

    @PostMapping
    @Transactional
    public void createConsole(@RequestBody ConsoleData request){
        var prodRaw = ProducerRepo.findById(request.producerId());

        Producer p = new Producer();
        var prod = p.OptionalToProducer(prodRaw);

        KvXGroup.CollectionRegistor.console.Console c = new KvXGroup.CollectionRegistor.console.Console(request, prod);

        ConsoleRepo.save(c);
    }


}
