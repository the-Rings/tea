package rain.mocking.binarytea.controller;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.FileSystemResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.io.File;

/**
 * Arthas监控文件下载
 */
@RestController
@RequestMapping("/arthas")
public class ArthasProfilerController {

    @GetMapping("/profiler-download")
    public ResponseEntity<FileSystemResource> downloadProfilerFile(@RequestParam String filename) {
        // 指定Arthas profiler生成的文件路径
        if (StringUtils.isBlank(filename)) {
            filename = "svg";
        }
        String filePath = "/app/arthas-output/" + filename;

        // 检查文件是否存在
        File file = new File(filePath);
        if (!file.exists()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }

        // 返回文件
        FileSystemResource resource = new FileSystemResource(file);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=" + file.getName());

        return ResponseEntity.ok()
                .headers(headers)
                .body(resource);
    }
}
