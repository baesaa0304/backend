package com.woozuda.backend.ai_recall.service;

import com.woozuda.backend.account.entity.UserEntity;
import com.woozuda.backend.account.repository.UserRepository;
import com.woozuda.backend.ai_recall.dto.*;
import com.woozuda.backend.ai_recall.entity.Airecall_4fs;
import com.woozuda.backend.ai_recall.entity.Airecall_kpt;
import com.woozuda.backend.ai_recall.entity.Airecall_pmi;
import com.woozuda.backend.ai_recall.entity.Airecall_scs;
import com.woozuda.backend.ai_recall.repository.fourfs.AiRecall4fsRpository;
import com.woozuda.backend.ai_recall.repository.kpt.AiRecallkptRpository;
import com.woozuda.backend.ai_recall.repository.pmi.AiRecallpmiRpository;
import com.woozuda.backend.ai_recall.repository.scs.AiRecallscsRpository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@RequiredArgsConstructor
@Slf4j
public class AiRecallService {

    private final AiRecall4fsRpository aiRecall4fsRpository;
    private final AiRecallkptRpository aiRecallkptRpository;
    private final AiRecallpmiRpository aiRecallpmiRpository;
    private final AiRecallscsRpository aiRecallscsRpository;
    private final UserRepository userRepository;

    /**
     * 4FS
     * @param airecall_4fs_dto
     */

    public void saveAirecall_4fs(Airecall_4fs_DTO airecall_4fs_dto) {
        UserEntity username = userRepository.findByUsername(airecall_4fs_dto.getUsername());
        Airecall_4fs aiRecall4fs = Airecall_4fs.toairecall4fsEntity(airecall_4fs_dto , username);

        aiRecall4fsRpository.save(aiRecall4fs);
        log.info("Airecall 저장 완료: {}", aiRecall4fs);
    }

    public Airecall_4fs_ResponseDTO getAirecall4fs(LocalDate start_date, LocalDate end_date, String username) {
        Airecall_4fs airecall_4fs = aiRecall4fsRpository.findByAirecall4FS(start_date, end_date, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~"));
        return  new Airecall_4fs_ResponseDTO(
                airecall_4fs.getStart_date(),
                airecall_4fs.getEnd_date(),
                airecall_4fs.getPatternAnalysis(),
                airecall_4fs.getPositiveBehavior(),
                airecall_4fs.getImprovementSuggest(),
                airecall_4fs.getUtilizationTips()
        );
    }

    /**
     * KTP
     * @param airecall_kpt_dto
     */
    public void saveAirecall_ktp(Airecall_Kpt_DTO airecall_kpt_dto) {
        UserEntity username = userRepository.findByUsername(airecall_kpt_dto.getUsername());
        Airecall_kpt aiReacllkpt = Airecall_kpt.toairecallktpEntity(airecall_kpt_dto, username);

        aiRecallkptRpository.save(aiReacllkpt);
        log.info("Airecall 저장 완료: {}", aiReacllkpt);
    }

    public Airecall_Kpt_ResponseDTO getAirecallktp(LocalDate start_date, LocalDate end_date, String username) {
        Airecall_kpt airecall_kpt = aiRecallkptRpository.findByAirecallktp(start_date, end_date, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~"));
        return  new Airecall_Kpt_ResponseDTO(
                airecall_kpt.getStart_date(),
                airecall_kpt.getEnd_date(),
                airecall_kpt.getStrength_analysis(),
                airecall_kpt.getImprovement(),
                airecall_kpt.getScalability()
        );
    }


    /**
     * PMI
     * @param airecall_pmi_dto
     */
    public void saveAirecall_pmi(Airecall_Pmi_DTO airecall_pmi_dto) {
        UserEntity username = userRepository.findByUsername(airecall_pmi_dto.getUsername());
        Airecall_pmi aiReacllpmi = Airecall_pmi.toairecallpmiEntity(airecall_pmi_dto , username);

        aiRecallpmiRpository.save(aiReacllpmi);
        log.info("Airecall 저장 완료: {}", aiReacllpmi);
    }

    public Airecall_Pmi_ResponseDTO getAirecallpmi(LocalDate start_date, LocalDate end_date, String username) {
        Airecall_pmi airecall_pmi = aiRecallpmiRpository.findByAirecallpmi(start_date, end_date, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~"));
        return new Airecall_Pmi_ResponseDTO(
                airecall_pmi.getStart_date(),
                airecall_pmi.getEnd_date(),
                airecall_pmi.getPositive(),
                airecall_pmi.getMinus(),
                airecall_pmi.getInteresting(),
                airecall_pmi.getConclusion_action()
        );
    }

    /**
     * SCS
     * @param airecall_scs_dto
     */

    public void saveAirecall_scs(Airecll_Scs_DTO airecall_scs_dto) {
        UserEntity username = userRepository.findByUsername(airecall_scs_dto.getUsername());
        Airecall_scs aiReacllscs = Airecall_scs.toairecallscsEntity(airecall_scs_dto , username);

        aiRecallscsRpository.save(aiReacllscs);
        log.info("Airecall 저장 완료: {}", aiReacllscs);
    }

    public Airecll_Scs_ResponseDTO getAirecallscs(LocalDate start_date, LocalDate end_date, String username) {
        Airecall_scs airecall_scs = aiRecallscsRpository.findByAirecallscs(start_date, end_date, username)
                .orElseThrow(() -> new IllegalArgumentException("분석 결과 없음~"));
        return new Airecll_Scs_ResponseDTO(
                airecall_scs.getStart_date(),
                airecall_scs.getEnd_date(),
                airecall_scs.getStart_summary(),
                airecall_scs.getStart_strength(),
                airecall_scs.getStart_suggestion(),
                airecall_scs.getContinue_summary(),
                airecall_scs.getContinue_strength(),
                airecall_scs.getContinue_suggestion(),
                airecall_scs.getStop_summary(),
                airecall_scs.getStop_strength(),
                airecall_scs.getStop_suggestion(),
                airecall_scs.getStart_improvement_plan(),
                airecall_scs.getContinue_improvement_plan(),
                airecall_scs.getStop_improvement_plan()
        );
    }

}
