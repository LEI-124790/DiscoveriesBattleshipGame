# Git Workflow

## Discoveries Battleship Game

Este documento descreve as práticas de controlo de versões utilizadas pela equipa durante o desenvolvimento do **Discoveries Battleship Game**.

O objetivo é documentar a utilização do Git através da linha de comandos, a gestão de branches, resolução de conflitos, utilização de `stash`, `rebase` e tags, bem como comparar diferentes formas de interação com o Git.

---

## 1. Estrutura de trabalho do Git

Durante o desenvolvimento distinguimos quatro áreas principais:

### Working Area

A **Working Area** corresponde aos ficheiros existentes localmente no projeto e que estão atualmente a ser editados.

Uma alteração realizada num ficheiro começa por existir apenas nesta área.

O comando:

```bash
git status
```

permite verificar quais os ficheiros modificados, adicionados ou removidos.

---

### Staging Area

A **Staging Area** contém as alterações que foram selecionadas para integrar o próximo commit.

Para adicionar um ficheiro:

```bash
git add <ficheiro>
```

Para adicionar todas as alterações:

```bash
git add .
```

É possível verificar o estado da staging area através de:

```bash
git status
```

---

### Local Repository

O **Local Repository** contém o histórico de commits existente na máquina do programador.

Depois de colocar as alterações na staging area, estas podem ser registadas através de:

```bash
git commit -m "mensagem do commit"
```

Exemplo utilizado no projeto:

```bash
git commit -m "docs(git): add Git workflow documentation"
```

---

### Remote Repository

O **Remote Repository** corresponde, neste projeto, ao repositório alojado no GitHub.

As alterações existentes no repositório local são enviadas para o GitHub através de:

```bash
git push
```

As alterações existentes no GitHub podem ser obtidas através de:

```bash
git pull
```

A ligação ao repositório remoto pode ser consultada através de:

```bash
git remote -v
```

---

## 2. Operações fundamentais em Git

### `git init`

Cria um novo repositório Git numa diretoria.

```bash
git init
```

No nosso caso, o projeto já existia num repositório remoto, pelo que o procedimento utilizado normalmente foi `git clone`.

---

### `git clone`

Cria uma cópia local do repositório remoto.

```bash
git clone https://github.com/LEI-124790/DiscoveriesBattleshipGame.git
```

Depois do clone:

```bash
cd DiscoveriesBattleshipGame
```

---

### `git add`

Adiciona alterações à staging area.

Exemplo:

```bash
git add docs/git-workflow.md
```

---

### `git commit`

Regista na repository local as alterações anteriormente adicionadas à staging area.

```bash
git commit -m "docs(git): document Git command-line workflow"
```

---

### `git push`

Envia os commits locais para o repositório remoto.

```bash
git push
```

Para publicar uma nova branch:

```bash
git push -u origin nome-da-branch
```

---

### `git pull`

Obtém alterações existentes no repositório remoto e integra-as na branch local.

```bash
git pull origin main
```

Antes de começar uma nova tarefa procuramos atualizar a branch `main`:

```bash
git checkout main
git pull origin main
```

---

## 3. Gestão de branches

As branches permitem desenvolver funcionalidades de forma isolada sem alterar diretamente a branch principal.

A branch principal deste projeto é:

```text
main
```

### Consultar branches

```bash
git branch
```

Para incluir branches remotas:

```bash
git branch -a
```

---

### Criar uma branch

```bash
git branch LEI-124790-git-workflow
```

---

### Alterar para outra branch

```bash
git checkout LEI-124790-git-workflow
```

Também é possível criar e mudar para a branch num único comando:

```bash
git checkout -b LEI-124790-git-workflow
```

---

## 4. Merge

O comando `merge` permite integrar as alterações de uma branch noutra.

Exemplo:

```bash
git checkout main
git pull origin main
git merge LEI-124790-git-workflow
```

No nosso workflow normal, em vez de efetuar diretamente o merge local para `main`, as branches são publicadas no GitHub:

```bash
git push -u origin LEI-124790-git-workflow
```

É depois criado um **Pull Request**, permitindo que outro membro da equipa reveja as alterações antes da integração em `main`.

Esta abordagem permite:

- revisão por outros membros;
- discussão das alterações;
- associação das alterações a Issues;
- manutenção de um histórico de colaboração;
- utilização de GitHub Actions antes do merge.

---

## 5. Rebase

O `rebase` permite reaplicar os commits de uma branch sobre uma base mais recente.

Exemplo:

```bash
git checkout LEI-124790-git-workflow
git fetch origin
git rebase origin/main
```

Imagine-se o seguinte histórico:

```text
A---B---C main
     \
      D---E feature
```

Depois de atualizar `main`, pode existir:

```text
A---B---C---F main
     \
      D---E feature
```

Depois de:

```bash
git rebase main
```

o histórico passa conceptualmente a:

```text
A---B---C---F main
             \
              D'---E' feature
```

O `rebase` produz um histórico mais linear, uma vez que os commits da branch são reaplicados sobre a versão mais recente da branch de destino.

### Merge vs Rebase

O `merge` preserva explicitamente o ponto em que dois históricos foram unidos.

O `rebase`, por outro lado, reescreve os commits da branch para os colocar sobre outra base.

Como regra de segurança, evitamos fazer `rebase` de commits que já estejam publicados e a ser utilizados por outros membros da equipa, pois o processo altera o histórico dos commits.

---

## 6. Simulação e resolução de um merge conflict

Para compreender o funcionamento dos conflitos de merge, foi realizada uma experiência em que dois membros alteraram a mesma zona de um ficheiro.

### Passo 1 — Atualizar `main`

```bash
git checkout main
git pull origin main
```

### Passo 2 — Criar uma branch

```bash
git checkout -b LEI-124790-conflict-test
```

Foi alterada uma linha de um ficheiro partilhado e realizado um commit:

```bash
git add .
git commit -m "test(git): modify shared line for conflict exercise"
git push -u origin LEI-124790-conflict-test
```

Outro membro da equipa realizou uma alteração diferente sobre a mesma linha.

Quando as duas alterações foram posteriormente integradas, o Git não conseguiu decidir automaticamente qual deveria prevalecer.

O ficheiro apresentou marcadores semelhantes a:

```text
<<<<<<< HEAD
Alteração presente na branch atual.
=======
Alteração proveniente da outra branch.
>>>>>>> outra-branch
```

A resolução foi realizada manualmente, escolhendo o conteúdo final desejado e removendo os marcadores.

Depois da resolução:

```bash
git add <ficheiro>
git commit -m "fix(git): resolve merge conflict manually"
```

### Conclusão

Um conflito ocorre quando o Git não consegue combinar automaticamente alterações incompatíveis.

A resolução exige que o programador analise as diferentes versões e determine conscientemente qual deve ser o resultado final.

---

## 7. Utilização de `git stash`

O comando `git stash` permite guardar temporariamente alterações ainda não concluídas.

Por exemplo, se estivermos a trabalhar numa funcionalidade mas precisarmos de mudar rapidamente para outra branch:

```bash
git status
git stash
```

Depois disso é possível alterar de branch:

```bash
git checkout main
```

Para consultar os stashes existentes:

```bash
git stash list
```

Para recuperar as alterações:

```bash
git stash pop
```

A principal vantagem do `stash` é permitir interromper temporariamente um trabalho sem criar um commit incompleto apenas para conseguir mudar de branch.

---

## 8. Tags

As tags permitem identificar pontos específicos do histórico, sendo normalmente utilizadas para marcar versões.

Para criar uma tag:

```bash
git tag v1.0.0
```

Para consultar as tags:

```bash
git tag
```

Para enviar uma tag para o GitHub:

```bash
git push origin v1.0.0
```

Também é possível criar uma annotated tag:

```bash
git tag -a v1.0.0 -m "Discoveries Battleship Game v1.0.0"
```

e publicá-la:

```bash
git push origin v1.0.0
```

A tag `v1.0.0` pode posteriormente ser utilizada para criar uma **GitHub Release**, identificando uma versão concreta do projeto.

---

## 9. GitHub Flow

O **GitHub Flow** é um modelo simples de desenvolvimento baseado em branches de curta duração.

O processo utilizado é aproximadamente:

```text
main
  |
  +---- feature branch
           |
           +-- commits
           |
           +-- push
           |
           +-- Pull Request
           |
           +-- Code Review
           |
           +-- Merge
           |
          main
```

Um exemplo aplicado ao projeto:

```text
main
 |
 +-- LEI-124790-pr-notification
       |
       +-- implementação
       +-- commits
       +-- Pull Request
       +-- review por colega
       +-- merge
```

Este modelo adapta-se bem ao nosso projeto porque a equipa é pequena e as funcionalidades podem ser desenvolvidas em branches separadas e integradas frequentemente.

---

## 10. Git Flow

O **Git Flow** utiliza uma estrutura de branches mais complexa.

Normalmente inclui:

```text
main
develop
feature/*
release/*
hotfix/*
```

As novas funcionalidades são desenvolvidas a partir de `develop` e apenas versões estabilizadas são integradas em `main`.

Exemplo conceptual:

```text
main
 |
 +---------------------------- release
 |
develop
 |
 +---- feature/a
 |
 +---- feature/b
```

### Git Flow vs GitHub Flow

O Git Flow é útil em projetos com:

- ciclos formais de releases;
- múltiplas versões mantidas simultaneamente;
- equipas maiores;
- necessidade frequente de hotfixes.

O GitHub Flow é mais simples e adequado a:

- equipas pequenas;
- integração frequente;
- branches de curta duração;
- Pull Requests e revisão contínua.

Neste projeto foi privilegiada uma abordagem próxima do **GitHub Flow**, devido à dimensão reduzida da equipa e à utilização intensiva de Pull Requests.

---

## 11. GitHub Web vs IntelliJ IDEA

Durante o desenvolvimento foram utilizadas diferentes formas de interação com o Git e GitHub.

### GitHub Web

A interface Web é particularmente conveniente para:

- criar e gerir Issues;
- organizar o Product Backlog;
- criar e analisar Pull Requests;
- realizar Code Reviews;
- configurar reviewers e assignees;
- observar GitHub Actions;
- consultar o histórico;
- analisar a rede de commits;
- gerir Releases e tags;
- efetuar pequenas alterações a ficheiros de documentação.

É especialmente útil quando a alteração não exige executar ou testar localmente o projeto.

---

### IntelliJ IDEA

O IntelliJ IDEA é mais adequado para:

- edição de código Java;
- navegação entre classes;
- refactoring;
- análise de erros;
- compilação e execução do projeto;
- criação de Javadoc;
- utilização integrada do Git;
- comparação visual de alterações;
- resolução de conflitos.

Para alterações significativas ao código, o IDE é normalmente mais conveniente do que a interface Web.

---

### Linha de comandos

A linha de comandos oferece maior controlo e permite compreender diretamente as operações realizadas pelo Git.

É particularmente útil para:

- `clone`;
- `add`;
- `commit`;
- `push`;
- `pull`;
- `branch`;
- `checkout`;
- `merge`;
- `rebase`;
- `stash`;
- `tag`.

A utilização conjunta das três interfaces permite escolher a ferramenta mais apropriada para cada situação.

---

## 12. Alterações concorrentes

A equipa utilizou branches diferentes para permitir que vários elementos trabalhassem simultaneamente.

O workflow seguido foi:

1. atualizar `main`;
2. criar uma branch;
3. realizar alterações;
4. fazer commits;
5. publicar a branch;
6. criar um Pull Request;
7. solicitar a revisão de outro membro;
8. corrigir eventuais problemas;
9. fazer merge para `main`.

Exemplo:

```bash
git checkout main
git pull origin main

git checkout -b LEI-124790-example

# realizar alterações

git add .
git commit -m "docs(example): add example change"
git push -u origin LEI-124790-example
```

A integração final é posteriormente efetuada através de Pull Request.

---

## 13. Utilização de LLMs

Foram utilizadas ferramentas baseadas em modelos de linguagem como apoio a tarefas de desenvolvimento, nomeadamente para:

- esclarecer comandos Git;
- apoiar a criação e revisão de documentação;
- analisar mensagens de erro;
- sugerir mensagens de commit;
- apoiar a compreensão de conflitos;
- auxiliar na criação de workflows de GitHub Actions.

As sugestões geradas por LLMs foram analisadas antes da sua utilização, uma vez que o programador continua responsável por validar se os comandos, documentação e código produzidos são adequados ao projeto.

---

## 14. Boas práticas adotadas

Durante o projeto procurámos aplicar as seguintes práticas:

- evitar alterações diretas desnecessárias em `main`;
- desenvolver trabalho em branches próprias;
- atualizar `main` antes de começar novas tarefas;
- escrever commits pequenos e relacionados com uma única alteração;
- utilizar mensagens de commit descritivas;
- utilizar Pull Requests;
- pedir revisão a outro membro da equipa;
- associar Pull Requests a Issues quando aplicável;
- resolver conflitos conscientemente;
- documentar funcionalidades e decisões relevantes;
- utilizar tags para identificar versões estáveis.

Exemplos de mensagens de commit adequadas:

```text
docs(readme): update three-shot turn rules
docs(git): add Git workflow documentation
ci(actions): add pull request notification workflow
docs(javadoc): improve Caravel documentation
fix(git): resolve merge conflict manually
```

---

## 15. Workflow geral utilizado

O fluxo normal de desenvolvimento pode ser resumido da seguinte forma:

```text
Issue
  ↓
Atribuição ao membro
  ↓
Branch
  ↓
Alterações
  ↓
git add
  ↓
git commit
  ↓
git push
  ↓
Pull Request
  ↓
GitHub Actions
  ↓
Code Review
  ↓
Merge
  ↓
Issue concluído
```

Este processo permite relacionar os requisitos do Product Backlog com as alterações concretas existentes no histórico do repositório.
