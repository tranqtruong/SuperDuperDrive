package dev.SuperDuperDrive.service;

import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

@Service
public class PagingService {
    public final List<Integer> PAGE_SIZES = Arrays.asList(5, 10, 50, 100);

    public boolean isValidPageSize(int pageSize) {
        return PAGE_SIZES.contains(pageSize);
    }

    public boolean isValidPageNumber(int pageNumber, int totalPages) {
        return pageNumber > 0 && pageNumber <= totalPages;
    }

    public int calculateStartPage(int pageNumber, int totalPages) {
        if (totalPages == 1) {
            return pageNumber;
        }
        if (totalPages < 3 && pageNumber == totalPages) {
            return pageNumber - 1;
        }

        if (pageNumber == 1) {
            return pageNumber;
        } else if (pageNumber > 1 && pageNumber < totalPages) {
            return pageNumber - 1;
        }
        return pageNumber - 2;
    }

    public int calculateEndPage(int startPage, int totalPages) {
        if (totalPages == 1) {
            return startPage;
        }
        if (totalPages < 3) {
            return startPage + 1;
        }

        return startPage + 2;
    }
}
