package Server.Game;

import Server.Game.Entities.GameEntity;
import Server.Game.Repositories.DownloadRepository;
import Server.Game.Repositories.GameRepository;
import Shared.Dto.GameDto;
import Shared.Enums.Error;
import Shared.Enums.Status;
import Shared.Enums.Title;
import Shared.Response;

import java.util.ArrayList;

public class GameService {
    GameRepository gameRepository;
    DownloadRepository downloadRepository;

    public GameService(){
        this.gameRepository = new GameRepository();
        this.downloadRepository = new DownloadRepository();
    }

    public Response findAll(){
        Response response = new Response();
        response.setTitle(Title.findAllGames);
        response.setData(this.gameRepository.findAll());
        response.setStatus(Status.successful);
        return response;
    }

    public Response findOne(GameDto gameDto){
        Response response = new Response();
        response.setTitle(Title.findOneGame);
        GameEntity game = this.gameRepository.findOne(gameDto);
        if(game.getId() == null){
            return response;
        }
        response.setData(game);
        response.setStatus(Status.successful);
        return response;
    }

    public Response download(GameDto gameDto){
        Response response = new Response();
        response.setTitle(Title.download);

        if (findOne(gameDto).getStatus().equals(Status.failed)){
            response.setError(Error.doesntExist);
            return response;
        }

        if (this.downloadRepository.doesRowExist(gameDto)){
             if(!this.downloadRepository.updateDownloadCount(gameDto)){
                 response.setError(Error.databaseError);
                 return response;
             }
        } else {
            if(!this.downloadRepository.insertIntoDownloads(gameDto)){
                response.setError(Error.databaseError);
                return response;
            }
        }

        String path = this.gameRepository.findPath(gameDto);
        response.setData(path);
        response.setStatus(Status.successful);

        return response;
    }
}
