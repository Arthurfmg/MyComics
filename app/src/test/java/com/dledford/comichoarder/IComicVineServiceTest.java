package com.dledford.comichoarder;

import com.arthurfmg.mycomics.rest.model.ComicVineCharacterModel;
import com.arthurfmg.mycomics.rest.model.ComicVineIssueModel;
import com.arthurfmg.mycomics.rest.services.ComicVineService;

import junit.framework.Assert;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class IComicVineServiceTest {
    @Test
    public void testComicVineCharacterByName() throws Exception {
        try {
            List<ComicVineCharacterModel> characters = new ComicVineService().findCharacterByNameSync("spider-man");
            for (ComicVineCharacterModel character : characters) {
                Assert.assertNotNull(character.getName());
                System.out.println(character.getName());
            }
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    public void testComicVineIssueById() throws Exception {
        Long issueId = 105342L;
        try {
            ComicVineIssueModel issue = new ComicVineService().findIssueByIdSync(issueId);
            Assert.assertNotNull(issue.getName());
            System.out.println(issue.getName());
        } catch (Exception e) {
            System.out.println(e);
        }

    }

    @Test
    public void testComicVineSeriesByName() throws Exception {
        try {
            List<ComicVineCharacterModel> characters = new ComicVineService().findSeriesByNameSync("Batman");
            for (ComicVineCharacterModel character : characters) {
                Assert.assertNotNull(character.getName());
                System.out.println(character.getName());
            }
        } catch (Exception e) {
            System.out.println(e);
        }
    }

    @Test
    public void testComicVineCharacterById() throws Exception {
        Long id = 1443L;
        try {
            ComicVineCharacterModel character = new ComicVineService().findCharacterByIdSync(id);
            Assert.assertNotNull(character.getName());
            System.out.println(character.getName());
        } catch (Exception e) {
            System.out.println(e);
        }
    }
}