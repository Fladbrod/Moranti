package gruppe1.moranti.restcontrollers;

import gruppe1.moranti.models.Case;
import gruppe1.moranti.repositories.CaseRepository;
import gruppe1.moranti.repositories.WaitingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
public class WaitingList {

    private final static long ID = 1;

    @Autowired
    WaitingListRepository waitingListRepository;

    @Autowired
    CaseRepository caseRepository;

    @GetMapping("/waitinglist")
    public WaitingList getWaitingList() {
        WaitingList waitingList;
        try {
            waitingList = waitingListRepository.findAll().get(0);

        } catch (IndexOutOfBoundsException e) {
            waitingList = new WaitingList();
            waitingListRepository.save(waitingList);
        }
        return waitingList;
    }

    @PostMapping("/waitinglist/addcase")
    public Case addCase(@RequestBody Case newCase) {
        Case saveCase = caseRepository.save(newCase);
        WaitingList waitingList = waitingListRepository.findAll().get(0);
        waitingList.addCase(saveCase);
        waitingListRepository.save(waitingList);
        return saveCase;
    }

    @DeleteMapping("/waitinglist/removeCase/{caseNumber}")
    public void removeCaseByCaseNumber(@PathVariable Long caseNumber) {
        WaitingList waitingList = waitingListRepository.findAll().get(0);
        waitingList.removeCaseByCaseNumber(caseNumber);
        caseRepository.deleteById(caseNumber);
        waitingListRepository.save(waitingList);
    }


}
